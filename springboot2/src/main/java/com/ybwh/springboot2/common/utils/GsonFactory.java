package com.ybwh.springboot2.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述: 产生有相关配置的gson对象的工厂类<br/>
 * 主要解决了前台传过来的空白字符串(如age:""或age:" ")转换为日期或数字类型时抛出异常的麻烦， 现在遇到此情况，会转化为null但不抛出异常。
 * 
 * <pre>
 * Gson gson = GsonFactory.newInstance(&quot;yyyy-MM-dd HH:mm:ss&quot;).create();
 * Entity e = gson.fromJson(jsonStr, Entity.class);
 * </pre>
 * 
 * @author fanbeibei
 * @since 2015年1月22日
 */
public class GsonFactory {
	public static final String yyyy_MM_dd_HH_mm_ss= "yyyy-MM-dd HH:mm:ss";
	public static final String yyyy_MM_dd= "yyyy-MM-dd";
	private static GsonFactory factory;
	private GsonBuilder gsonBuilder = new GsonBuilder();

	private GsonFactory() {

	}

	private GsonBuilder getGsonBuilder() {
		return gsonBuilder;
	}

	/**
	 * 创建有相关配置的gson对象
	 * 
	 * @author fanbeibei
	 * @return
	 * @since 2015年1月22日
	 */
	public Gson createGson() {
		return gsonBuilder.create();

	}

	/**
	 * 默认的gson日期格式为yyyy-MM-dd
	 * 
	 * @author fanbeibei
	 * @return
	 * @since 2015年1月22日
	 */
	public static GsonFactory newInstance() {
		
		if (null == factory) {
			factory = new GsonFactory();
			factory.getGsonBuilder()
			.registerTypeAdapter(Date.class, new DateAdapter("yyyy-MM-dd"))
			.registerTypeAdapter(Integer.class, new IntegerAdapter())
			.registerTypeAdapter(Long.class, new LongAdapter())
			.registerTypeAdapter(Float.class, new FloatAdapter())
			.registerTypeAdapter(Short.class, new ShortAdapter())
			.registerTypeAdapter(Double.class, new DoubleAdapter())
			.registerTypeAdapter(Boolean.class, new BooleanAdapter());
		}
		
		return factory;

	}

	/**
	 * @author fanbeibei
	 * @param datePattern
	 *            日期格式
	 * @return
	 * @since 2015年1月22日
	 */
	public static GsonFactory newInstance(String datePattern) {
		GsonFactory factory = new GsonFactory();

		factory.getGsonBuilder()
			.registerTypeAdapter(Date.class, new DateAdapter(datePattern))
			.registerTypeAdapter(Integer.class, new IntegerAdapter())
			.registerTypeAdapter(Long.class, new LongAdapter())
			.registerTypeAdapter(Float.class, new FloatAdapter())
			.registerTypeAdapter(Short.class, new ShortAdapter())
			.registerTypeAdapter(Double.class, new DoubleAdapter())
			.registerTypeAdapter(Boolean.class, new BooleanAdapter());

		return factory;

	}

	/**
	 * 描述: Integer类型转化器，空字符串转化为Integer时不会抛异常
	 * 
	 * @author fanbeibei
	 * @since 2015年1月15日
	 */
	private static class IntegerAdapter extends TypeAdapter<Integer> {

		@Override
		public void write(JsonWriter out, Integer value) throws IOException {
			if (null == value) {
				out.nullValue();
				return;
			}

			out.value(value);

		}

		@Override
		public Integer read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			String val = in.nextString();
			if (null != val && !"".equals(val.trim())) {
				return Integer.parseInt(val);
			}

			return null;
		}

	}

	private static class ShortAdapter extends TypeAdapter<Short> {

		@Override
		public void write(JsonWriter out, Short value) throws IOException {
			if (null == value) {
				out.nullValue();
				return;
			}

			out.value(value);

		}

		@Override
		public Short read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			String val = in.nextString();
			if (null != val && !"".equals(val.trim())) {
				return Short.parseShort(val);
			}

			return null;
		}

	}

	private static class DoubleAdapter extends TypeAdapter<Double> {

		@Override
		public void write(JsonWriter out, Double value) throws IOException {
			if (null == value) {
				out.nullValue();
				return;
			}

			out.value(value);

		}

		@Override
		public Double read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			String val = in.nextString();
			if (null != val && !"".equals(val.trim())) {
				return Double.parseDouble(val);
			}

			return null;
		}

	}

	private static class BooleanAdapter extends TypeAdapter<Boolean> {

		@Override
		public void write(JsonWriter out, Boolean value) throws IOException {
			if (null == value) {
				out.nullValue();
				return;
			}

			out.value(value);

		}

		@Override
		public Boolean read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			String val = in.nextString();
			if (null != val && !"".equals(val.trim())) {
				return Boolean.parseBoolean(val);
			}

			return null;
		}

	}

	private static class FloatAdapter extends TypeAdapter<Float> {

		@Override
		public void write(JsonWriter out, Float value) throws IOException {
			if (null == value) {
				out.nullValue();
				return;
			}

			out.value(value);

		}

		@Override
		public Float read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			String val = in.nextString();
			if (null != val && !"".equals(val.trim())) {
				return Float.parseFloat(val);
			}

			return null;
		}

	}

	private static class LongAdapter extends TypeAdapter<Long> {

		@Override
		public void write(JsonWriter out, Long value) throws IOException {
			if (null == value) {
				out.nullValue();
				return;
			}

			out.value(value);

		}

		@Override
		public Long read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			String val = in.nextString();
			if (null != val && !"".equals(val.trim())) {
				return Long.parseLong(val);
			}

			return null;
		}

	}

	private static class DateAdapter extends TypeAdapter<Date> {

		private String pattern;

		public DateAdapter(String pattern) {
			this.pattern = pattern;
		}

		@Override
		public void write(JsonWriter out, Date value) throws IOException {
			if (null == value) {
				out.nullValue();
				return;
			}

			DateFormat df = new SimpleDateFormat(pattern);
			out.value(df.format(value));

		}

		@Override
		public Date read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			String val = in.nextString();
			if (null != val && !"".equals(val.trim())) {
				DateFormat df = new SimpleDateFormat(pattern);
				try {
					return df.parse(val);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

	}

}