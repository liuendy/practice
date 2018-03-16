/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ybwh.quartz.cron.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

/**
 * Date sequence generator for a
 * <a href="http://www.manpagez.com/man/5/crontab/">Crontab pattern</a>,
 * allowing clients to specify a pattern that the sequence matches.
 *
 * <p>The pattern is a list of six single space-separated fields: representing
 * second, minute, hour, day, month, weekday. Month and weekday names can be
 * given as the first three letters of the English names.
 *
 * <p>Example patterns:
 * <ul>
 * <li>"0 0 * * * *" = the top of every hour of every day.</li>
 * <li>"*&#47;10 * * * * *" = every ten seconds.</li>
 * <li>"0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.</li>
 * <li>"0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.</li>
 * <li>"0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays</li>
 * <li>"0 0 0 25 12 ?" = every Christmas Day at midnight</li>
 * </ul>
 *
 * @author Dave Syer
 * @author Juergen Hoeller
 * @since 3.0
 * @see CronTrigger
 */
public class CronSequenceGenerator {

	private final String expression;

	private final TimeZone timeZone;

	/**
	 * 12λ��ʾһ���12����
	 */
	private final BitSet months = new BitSet(12);

	/**
	 * 31λ��ʾһ��������31��
	 */
	private final BitSet daysOfMonth = new BitSet(31);

	/**
	 * 7λ��ʾһ��7��
	 */
	private final BitSet daysOfWeek = new BitSet(7);

	/**
	 * 24λ��ʾһ���24��Сʱ
	 */
	private final BitSet hours = new BitSet(24);

	/**
	 * 60λ��ʾһ��Сʱ��60������
	 */
	private final BitSet minutes = new BitSet(60);

	/**
	 * 60λ��ʾһ���ӵ�60������
	 */
	private final BitSet seconds = new BitSet(60);


	/**
	 * Construct a {@link CronSequenceGenerator} from the pattern provided,
	 * using the default {@link TimeZone}.
	 * @param expression a space-separated list of time fields
	 * @throws IllegalArgumentException if the pattern cannot be parsed
	 * @see java.util.TimeZone#getDefault()
	 */
	public CronSequenceGenerator(String expression) {
		this(expression, TimeZone.getDefault());
	}

	/**
	 * Construct a {@link CronSequenceGenerator} from the pattern provided,
	 * using the specified {@link TimeZone}.
	 * @param expression a space-separated list of time fields
	 * @param timeZone the TimeZone to use for generated trigger times
	 * @throws IllegalArgumentException if the pattern cannot be parsed
	 */
	public CronSequenceGenerator(String expression, TimeZone timeZone) {
		this.expression = expression;
		this.timeZone = timeZone;
		parse(expression);
	}


	/**
	 * Return the cron pattern that this sequence generator has been built for.
	 */
	String getExpression() {
		return this.expression;
	}


	/**
	 * Get the next {@link Date} in the sequence matching the Cron pattern and
	 * after the value provided. The return value will have a whole number of
	 * seconds, and will be after the input value.
	 * @param date a seed value
	 * @return the next value matching the pattern
	 */
	public Date next(Date date) {
		/*
		The plan:

		1 Round up to the next whole second

		2 If seconds match move on, otherwise find the next match:
		2.1 If next match is in the next minute then roll forwards

		3 If minute matches move on, otherwise find the next match
		3.1 If next match is in the next hour then roll forwards
		3.2 Reset the seconds and go to 2

		4 If hour matches move on, otherwise find the next match
		4.1 If next match is in the next day then roll forwards,
		4.2 Reset the minutes and seconds and go to 2

		...
		*/

		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(this.timeZone);
		calendar.setTime(date);

		// First, just reset the milliseconds and try to calculate from there...
		calendar.set(Calendar.MILLISECOND, 0);
		long originalTimestamp = calendar.getTimeInMillis();
		doNext(calendar, calendar.get(Calendar.YEAR));

		if (calendar.getTimeInMillis() == originalTimestamp) {
			// We arrived at the original timestamp - round up to the next whole second and try again...
			calendar.add(Calendar.SECOND, 1);
			doNext(calendar, calendar.get(Calendar.YEAR));
		}

		return calendar.getTime();
	}

	/**
	 * ��������֡�ʱ���ա����𲽼��ָ��ʱ���ֵ������������ϵ�ֵ���Ѿ����Ϲ�����ôָ��ʱ�����cron����ʽ��
	 * �㷨�����������ĳ�����ֵ�����Ϲ��򣬵���������һ�����Ϲ����ֵ(�����ǰ�Ѿ�������󸺺ɹ����ֵ��
	 * ��������Ϊ0���ݹ�������ߵ�����һ�����ɹ����ֵ)�������ϵ����ֵ��������С���ɹ����ֵ��Ȼ����뿪ʼ���¼��͵�����
	 * 
	 * @param calendar ʱ��
	 * @param dot
	 */
	private void doNext(Calendar calendar, int dot) {
		List<Integer> resets = new ArrayList<Integer>();

		int second = calendar.get(Calendar.SECOND);
		List<Integer> emptyList = Collections.emptyList();
		int updateSecond = findNext(this.seconds, second, calendar, Calendar.SECOND, Calendar.MINUTE, emptyList);
		if (second == updateSecond) {
			resets.add(Calendar.SECOND);
		}

		int minute = calendar.get(Calendar.MINUTE);
		int updateMinute = findNext(this.minutes, minute, calendar, Calendar.MINUTE, Calendar.HOUR_OF_DAY, resets);
		if (minute == updateMinute) {
			resets.add(Calendar.MINUTE);
		}
		else {
			doNext(calendar, dot);
		}

		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int updateHour = findNext(this.hours, hour, calendar, Calendar.HOUR_OF_DAY, Calendar.DAY_OF_WEEK, resets);
		if (hour == updateHour) {
			resets.add(Calendar.HOUR_OF_DAY);
		}
		else {
			doNext(calendar, dot);
		}

		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int updateDayOfMonth = findNextDay(calendar, this.daysOfMonth, dayOfMonth, daysOfWeek, dayOfWeek, resets);
		if (dayOfMonth == updateDayOfMonth) {
			resets.add(Calendar.DAY_OF_MONTH);
		}
		else {
			doNext(calendar, dot);
		}

		int month = calendar.get(Calendar.MONTH);
		int updateMonth = findNext(this.months, month, calendar, Calendar.MONTH, Calendar.YEAR, resets);
		if (month != updateMonth) {
			if (calendar.get(Calendar.YEAR) - dot > 4) {
				throw new IllegalArgumentException("Invalid cron expression \"" + this.expression +
						"\" led to runaway search for next trigger");
			}
			doNext(calendar, dot);
		}

	}

	private int findNextDay(Calendar calendar, BitSet daysOfMonth, int dayOfMonth, BitSet daysOfWeek, int dayOfWeek,
			List<Integer> resets) {

		int count = 0;
		int max = 366;
		// the DAY_OF_WEEK values in java.util.Calendar start with 1 (Sunday),
		// but in the cron pattern, they start with 0, so we subtract 1 here
		while ((!daysOfMonth.get(dayOfMonth) || !daysOfWeek.get(dayOfWeek - 1)) && count++ < max) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			reset(calendar, resets);
		}
		if (count >= max) {
			throw new IllegalArgumentException("Overflow in day for expression \"" + this.expression + "\"");
		}
		return dayOfMonth;
	}

	/**
	 * Search the bits provided for the next set bit after the value provided,
	 * and reset the calendar.
	 * @param bits a {@link BitSet} representing the allowed values of the field
	 * @param value the current value of the field
	 * @param calendar the calendar to increment as we move through the bits
	 * @param field the field to increment in the calendar (@see
	 * {@link Calendar} for the static constants defining valid fields)
	 * @param lowerOrders the Calendar field ids that should be reset (i.e. the
	 * ones of lower significance than the field of interest)
	 * @return the value of the calendar field that is next in the sequence
	 */
	private int findNext(BitSet bits, int value, Calendar calendar, int field, int nextField, List<Integer> lowerOrders) {
		int nextValue = bits.nextSetBit(value);
		// roll over if needed
		if (nextValue == -1) {
			calendar.add(nextField, 1);
			reset(calendar, Arrays.asList(field));
			nextValue = bits.nextSetBit(0);
		}
		if (nextValue != value) {
			calendar.set(field, nextValue);
			reset(calendar, lowerOrders);
		}
		return nextValue;
	}

	/**
	 * Reset the calendar setting all the fields provided to zero.
	 */
	private void reset(Calendar calendar, List<Integer> fields) {
		for (int field : fields) {
			calendar.set(field, field == Calendar.DAY_OF_MONTH ? 1 : 0);
		}
	}


	// Parsing logic invoked by the constructor

	/**
	 * Parse the given pattern expression.
	 * �߼��ǽ�������ı���ʽ������BitSet
	 */
	private void parse(String expression) throws IllegalArgumentException {
		String[] fields = StringUtils.tokenizeToStringArray(expression, " ");
		if (fields.length != 6) {
			throw new IllegalArgumentException(String.format(
					"Cron expression must consist of 6 fields (found %d in \"%s\")", fields.length, expression));
		}
		setNumberHits(this.seconds, fields[0], 0, 60);
		setNumberHits(this.minutes, fields[1], 0, 60);
		setNumberHits(this.hours, fields[2], 0, 24);
		
		setDaysOfMonth(this.daysOfMonth, fields[3]);//�캬��"?"��Ҫ��������
		setMonths(this.months, fields[4]);//�º���Ӣ��ö��ֵ��Ҫ��������
		setDays(this.daysOfWeek, replaceOrdinals(fields[5], "SUN,MON,TUE,WED,THU,FRI,SAT"), 8);//�ܺ���"?"��Ӣ��ö��ֵ��Ҫ��������
		
		if (this.daysOfWeek.get(7)) {
			// Sunday can be represented as 0 or 7
			this.daysOfWeek.set(0);
			this.daysOfWeek.clear(7);
		}
	}

	/**
	 * ���ǽ�Ӣ��ö��ֵ�滻�����֣�0��ʼ�����滻��Ϳ��Ը���setNumberHits���߼�
	 * 
	 * Replace the values in the comma-separated list (case insensitive)
	 * with their index in the list.
	 * @return a new String with the values from the list replaced
	 */
	private String replaceOrdinals(String value, String commaSeparatedList) {
		
		String[] list = StringUtils.commaDelimitedListToStringArray(commaSeparatedList);//���ŷָ�������
		for (int i = 0; i < list.length; i++) {
			String item = list[i].toUpperCase();
			value = StringUtils.replace(value.toUpperCase(), item, "" + i);
		}
		return value;
	}

	/**
	 * ��������߼�
	 * @param bits
	 * @param field
	 */
	private void setDaysOfMonth(BitSet bits, String field) {
		int max = 31;
		// Days of month start with 1 (in Cron and Calendar) so add one
		setDays(bits, field, max + 1);
		// ... and remove it from the front
		bits.clear(0);
	}

	private void setDays(BitSet bits, String field, int max) {
		if (field.contains("?")) {//"?"��ֻ�����պ�����ʹ�ã���ʾ����ȷ��ֵ��ʵ�����õ�ͬ"*"����ƥ������ֵ������ֱ�ӻ���"*"
			field = "*";
		}
		setNumberHits(bits, field, 0, max);
	}

	/**
	 * �����·ݵ��߼�
	 * 
	 * @param bits
	 * @param value
	 */
	private void setMonths(BitSet bits, String value) {
		int max = 12;
		/**
		 * ��Ӣ��ö��ֵ�滻�����֣�0��ʼ�����滻��Ϳ��Ը���setNumberHits���߼�
		 */
		value = replaceOrdinals(value, "FOO,JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC");
		BitSet months = new BitSet(13);
		
		
		/**
		 * cron����ʽ�����õ��·ݷ�Χ��1-12��Calendar�е��·ݷ�Χ��0-11,���Խ�monthsλ������������1λ��
		 */
		// Months start with 1 in Cron and 0 in Calendar, so push the values first into a longer bit set
		setNumberHits(months, value, 1, max + 1);
		// ... and then rotate it to the front of the months
		for (int i = 1; i <= max; i++) {
			if (months.get(i)) {
				bits.set(i - 1);
			}
		}
	}

	/**
	 * ����ʱ������߼�
	 * 
	 * @param bits 
	 * @param value ����ʽ
	 * @param min ��Чλ�ÿ�ʼ
	 * @param max ��Чλ�ý���
	 */
	private void setNumberHits(BitSet bits, String value, int min, int max) {
		/**
		 * ���Ⱥ��ж��ţ���ʾ�������ʽ�Ǹ���ϱ���ʽ���ȷָ���ӱ���ʽһ��������
		 */
		String[] fields = StringUtils.delimitedListToStringArray(value, ",");
		
		for (String field : fields) {
			
			/**
			 * cron����ʽ������ʽ��������1.ȷ��Ҫ���ñ���λ�ķ�Χ��2.���ñ���λֵ
			 */
			if (!field.contains("/")) {//����"/"���߼�
				// Not an incrementer so it must be a range (possibly empty)
				int[] range = getRange(field, min, max);
				
				
				
				bits.set(range[0], range[1] + 1);
			}
			else {//  "/"�������������ʼʱ�䴥��һ�Σ�Ȼ��ÿ���̶�ʱ�䴥��һ�Σ�ֱ������ʱ�䡣
				/**
				 * ��"/"����ʽ����
				 * 1. "/"ǰ��ı���ʽָ����ʼʱ��ͽ���ʱ�䣬������"-"������Ҳ���Բ���"-"��ָֻ����ʼʱ��
				 * 2. "/"����ı���ʽ��ʾ�����ʱ��
				 */
				String[] split = StringUtils.delimitedListToStringArray(field, "/");
				
				
				//"/"ǰ�ı���ʽ�Ľ���
				if (split.length > 2) {
					throw new IllegalArgumentException("Incrementer has more than two fields: '" +
							field + "' in expression \"" + this.expression + "\"");
				}
				
				// �����ڷ�����ʹ��"10-30/2"��ʾ��Сʱ��10���ӿ�ʼÿ��2���Ӵ���һ�Σ�ֱ��ÿСʱ��30���ӽ�����
				int[] range = getRange(split[0], min, max);
				
				if (!split[0].contains("-")) {//����"-"�Ľ���
					range[1] = max - 1;
				}
				
				
				
				//"/"����ı���ʽ�Ľ���
				int delta = Integer.valueOf(split[1]);
				if (delta <= 0) {
					throw new IllegalArgumentException("Incrementer delta must be 1 or higher: '" +
							field + "' in expression \"" + this.expression + "\"");
				}
				
				
				//
				for (int i = range[0]; i <= range[1]; i += delta) {
					bits.set(i);
				}
			}
		}
	}

	/**
	 * ��������","��"/"�ı���ʽ
	 * @param field
	 * @param min
	 * @param max
	 * @return Ҫ����Ϊ1��BitSet����ʼ����ֹ��index
	 */
	private int[] getRange(String field, int min, int max) {
		int[] result = new int[2];
		if (field.contains("*")) {//"*"��ʾƥ����������ֵ
			result[0] = min;
			result[1] = max - 1;
			return result;
		}
		if (!field.contains("-")) {//
			result[0] = result[1] = Integer.valueOf(field);
		}
		else {//"-"��ʾ��Χ�ڵ�����ֵ��ȡ����βֵ����ʼλ�ü���
			String[] split = StringUtils.delimitedListToStringArray(field, "-");
			if (split.length > 2) {
				throw new IllegalArgumentException("Range has more than two fields: '" +
						field + "' in expression \"" + this.expression + "\"");
			}
			result[0] = Integer.valueOf(split[0]);
			result[1] = Integer.valueOf(split[1]);
		}
		
		
		//У�����ʽ�Ϸ���
		if (result[0] >= max || result[1] >= max) {
			throw new IllegalArgumentException("Range exceeds maximum (" + max + "): '" +
					field + "' in expression \"" + this.expression + "\"");
		}
		if (result[0] < min || result[1] < min) {
			throw new IllegalArgumentException("Range less than minimum (" + min + "): '" +
					field + "' in expression \"" + this.expression + "\"");
		}
		return result;
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CronSequenceGenerator)) {
			return false;
		}
		CronSequenceGenerator otherCron = (CronSequenceGenerator) other;
		return (this.months.equals(otherCron.months) && this.daysOfMonth.equals(otherCron.daysOfMonth) &&
				this.daysOfWeek.equals(otherCron.daysOfWeek) && this.hours.equals(otherCron.hours) &&
				this.minutes.equals(otherCron.minutes) && this.seconds.equals(otherCron.seconds));
	}

	@Override
	public int hashCode() {
		return (17 * this.months.hashCode() + 29 * this.daysOfMonth.hashCode() + 37 * this.daysOfWeek.hashCode() +
				41 * this.hours.hashCode() + 53 * this.minutes.hashCode() + 61 * this.seconds.hashCode());
	}

	@Override
	public String toString() {
		return (getClass().getSimpleName() + ": " + this.expression);
	}

}