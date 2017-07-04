package com.ybwh.hbase.spring;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;

/**
 * 行数据转对象
 * 
 * @author Fanbeibei
 * @date 2017年5月17日 下午5:38:35
 * 
 */
public class EntityMapper implements RowMapper<Object> {

	private Class<?> entityClass;

	/**
	 * @param entityClass
	 *            对象类型
	 */
	public EntityMapper(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public Object mapRow(Result result, int rowNum) throws Exception {
		Object obj = entityClass.newInstance();

		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {// 跳过静态字段
				if (field.isAnnotationPresent(HbaseColumn.class)) {
					setField(obj, result, field);
				}
			}
		}
		return obj;
	}

	private void setField(Object obj, Result result, Field field)
			throws IllegalArgumentException, IllegalAccessException {

		Class<?> filedType = field.getType();
		field.setAccessible(true);

		HbaseColumn column = field.getAnnotation(HbaseColumn.class);
		Cell cell = result.getColumnLatestCell(Bytes.toBytes(column.family()), Bytes.toBytes(column.qualifier()));
		
		if (null == cell) {
			return;
		}

		if (filedType.equals(String.class)) {
			field.set(obj, Bytes.toString(CellUtil.cloneValue(cell)));
		} else if (filedType.equals(Long.class) || filedType.equals(long.class)) {
			field.set(obj, Bytes.toLong(CellUtil.cloneValue(cell)));
		} else if (filedType.equals(Date.class)) {
			field.set(obj, new Date(Bytes.toLong(CellUtil.cloneValue(cell))));
		} else if (filedType.equals(Double.class) || filedType.equals(double.class)) {
			field.set(obj, Bytes.toDouble(CellUtil.cloneValue(cell)));
		} else if (filedType.equals(Float.class) || filedType.equals(float.class)) {
			field.set(obj, Bytes.toFloat(CellUtil.cloneValue(cell)));
		} else if (filedType.equals(Short.class) || filedType.equals(short.class)) {
			field.set(obj, Bytes.toShort(CellUtil.cloneValue(cell)));
		} else if (filedType.equals(Integer.class) || filedType.equals(int.class)) {
			field.set(obj, Bytes.toInt(CellUtil.cloneValue(cell)));
		} else if (filedType.equals(BigDecimal.class)) {
			field.set(obj, new BigDecimal(Bytes.toString(CellUtil.cloneValue(cell))));
		}

	}

}
