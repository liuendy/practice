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

package com.ybwh.cron.parser;

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
public class CronSequenceGenerator {//spring-task

	private final String expression;

	private final TimeZone timeZone;

	/**
	 * 12位表示一年的12个月
	 */
	private final BitSet months = new BitSet(12);

	/**
	 * 31位表示一个月最多的31天
	 */
	private final BitSet daysOfMonth = new BitSet(31);

	/**
	 * 7位表示一周7天
	 */
	private final BitSet daysOfWeek = new BitSet(7);

	/**
	 * 24位表示一天的24个小时
	 */
	private final BitSet hours = new BitSet(24);

	/**
	 * 60位表示一个小时的60个分钟
	 */
	private final BitSet minutes = new BitSet(60);

	/**
	 * 60位表示一秒钟的60个秒钟
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
	 * 沿着秒→分→时→日→月逐步检查指定时间的值。如果所有域上的值都已经符合规则那么指定时间符合cron表达式，
	 * 算法结束。如果有某个域的值不符合规则，调整该域到下一个符合规则的值(如果当前已经超过最大负荷规则的值，
	 * 则自身设为0，递归调整更高的域到下一个负荷规则的值)，并将较低域的值调整到最小负荷规则的值，然后从秒开始重新检查和调整。
	 * 
	 * @param calendar 时间
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
	 * 逻辑是将各个域的表达式解析成BitSet
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
		
		setDaysOfMonth(this.daysOfMonth, fields[3]);//天含有"?"需要单独解析
		setMonths(this.months, fields[4]);//月含有英文枚举值需要单独处理
		setDays(this.daysOfWeek, replaceOrdinals(fields[5], "SUN,MON,TUE,WED,THU,FRI,SAT"), 8);//周含有"?"和英文枚举值需要单独解析
		
		if (this.daysOfWeek.get(7)) {
			// Sunday can be represented as 0 or 7
			this.daysOfWeek.set(0);
			this.daysOfWeek.clear(7);
		}
	}

	/**
	 * 就是将英文枚举值替换成数字（0开始），替换后就可以复用setNumberHits的逻辑
	 * 
	 * Replace the values in the comma-separated list (case insensitive)
	 * with their index in the list.
	 * @return a new String with the values from the list replaced
	 */
	private String replaceOrdinals(String value, String commaSeparatedList) {
		
		String[] list = StringUtils.commaDelimitedListToStringArray(commaSeparatedList);//逗号分隔成数组
		for (int i = 0; i < list.length; i++) {
			String item = list[i].toUpperCase();
			value = StringUtils.replace(value.toUpperCase(), item, "" + i);
		}
		return value;
	}

	/**
	 * 处理天的逻辑
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
		if (field.contains("?")) {//"?"：只能在日和周域使用，表示非明确的值，实际作用等同"*"，即匹配任意值。所以直接换成"*"
			field = "*";
		}
		setNumberHits(bits, field, 0, max);
	}

	/**
	 * 处理月份的逻辑
	 * 
	 * @param bits
	 * @param value
	 */
	private void setMonths(BitSet bits, String value) {
		int max = 12;
		/**
		 * 将英文枚举值替换成数字（0开始），替换后就可以复用setNumberHits的逻辑
		 */
		value = replaceOrdinals(value, "FOO,JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC");
		BitSet months = new BitSet(13);
		
		
		/**
		 * cron表达式中配置的月份范围是1-12，Calendar中的月份范围是0-11,所以将months位数组整体左移1位。
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
	 * 解析时分秒的逻辑
	 * 
	 * @param bits 
	 * @param value 表达式
	 * @param min 有效位置开始
	 * @param max 有效位置结束
	 */
	private void setNumberHits(BitSet bits, String value, int min, int max) {
		/**
		 * 首先含有逗号，表示这个表达式是个组合表达式，先分割成子表达式一个个解析
		 */
		String[] fields = StringUtils.delimitedListToStringArray(value, ",");
		
		for (String field : fields) {
			
			/**
			 * cron表达式解析方式分两步：1.确定要设置比特位的范围；2.设置比特位值
			 */
			if (!field.contains("/")) {//不含"/"的逻辑
				// Not an incrementer so it must be a range (possibly empty)
				int[] range = getRange(field, min, max);
				
				
				
				bits.set(range[0], range[1] + 1);
			}
			else {//  "/"：间隔触发，起始时间触发一次，然后每隔固定时间触发一次，直到结束时间。
				/**
				 * 含"/"表达式规则：
				 * 1. "/"前面的表达式指定开始时间和结束时间，二者用"-"隔开；也可以不带"-"，只指定开始时间
				 * 2. "/"后面的表达式表示间隔的时间
				 */
				String[] split = StringUtils.delimitedListToStringArray(field, "/");
				
				
				//"/"前的表达式的解析
				if (split.length > 2) {
					throw new IllegalArgumentException("Incrementer has more than two fields: '" +
							field + "' in expression \"" + this.expression + "\"");
				}
				
				// 例如在分钟域使用"10-30/2"表示从小时的10分钟开始每隔2分钟触发一次，直到每小时的30分钟结束。
				int[] range = getRange(split[0], min, max);
				
				if (!split[0].contains("-")) {//不含"-"的解析
					range[1] = max - 1;
				}
				
				
				
				//"/"后面的表达式的解析
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
	 * 解析不含","和"/"的表达式
	 * @param field
	 * @param min
	 * @param max
	 * @return 要设置为1的BitSet的起始和终止的index
	 */
	private int[] getRange(String field, int min, int max) {
		int[] result = new int[2];
		if (field.contains("*")) {//"*"表示匹配该域的所有值
			result[0] = min;
			result[1] = max - 1;
			return result;
		}
		if (!field.contains("-")) {//
			result[0] = result[1] = Integer.valueOf(field);
		}
		else {//"-"表示范围内的所有值，取得首尾值做起始位置即可
			String[] split = StringUtils.delimitedListToStringArray(field, "-");
			if (split.length > 2) {
				throw new IllegalArgumentException("Range has more than two fields: '" +
						field + "' in expression \"" + this.expression + "\"");
			}
			result[0] = Integer.valueOf(split[0]);
			result[1] = Integer.valueOf(split[1]);
		}
		
		
		//校验表达式合法性
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
