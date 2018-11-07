package com.ybwh.springboot1.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 对普通对象进行属性值操作的工具类
 */
public class BeanUtil {

    /**
     * 获取当前类声明的private/protected变量
     */
    static public Object getFieldValue(Object object, String propertyName)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(propertyName);
        field.setAccessible(true);

        return field.get(object);
    }

    /**
     * 因为getFieldValue()方法，无法读取super class的属性的值； 所以本方法做出扩展，允许读取super class的属性的值；
     *
     * @param object
     * @param propertyName
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static Object getFieldValueFromAllSuper(Object object,
                                                   String propertyName) throws IllegalAccessException,
            NoSuchFieldException {
        Class<?> claszz = object.getClass();
        Field field = null;

        do {
            try {
                field = claszz.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
                // e.printStackTrace();
                field = null;
            }
            claszz = claszz.getSuperclass();
        } while (field == null && claszz != null);

        if (field == null)
            return null;

        field.setAccessible(true);
        return field.get(object);
    }

    /**
     * <p>
     * 描述: 通过get和set方法将fromObj的属性值<strong>浅拷贝</strong>覆盖toObj中的同名属性值,
     * 但fromObj中的空值(不包含空字符)不会覆盖toObj中的值。
     * </p>
     *
     * @param fromObj
     * @param toObj
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @author fanbeibei
     * @since 2015年2月11日
     */
    public static <T> void copyProperties(T fromObj, T toObj)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, SecurityException {
        if (null == fromObj || null == toObj) {
            throw new NullPointerException();
        }

        Method[] methods = fromObj.getClass().getMethods();

        Method setter = null;
        for (Method method : methods) {
            if (method.getName().length() > 3
                    && method.getName().startsWith("get")
                    && !method.getName().equals("getClass")) {
                Object value = method.invoke(fromObj, (Object[]) null);
                if (null == value) {
                    continue;
                } else {
                    try {
                        setter = toObj.getClass().getMethod(
                                "set" + method.getName().substring(3),
                                method.getReturnType());
                    } catch (NoSuchMethodException e) {
                        continue;
                    }

                    setter.invoke(toObj, value);
                }
            }
        }

    }

    /**
     * <p>
     * 描述: 通过get和set方法将fromObj的属性值<strong>浅拷贝</strong>覆盖toObj中的同名属性值,
     * 当copyNull为false时fromObj中的空值(不包含空字符)不会覆盖toObj中的值；否则覆盖
     * </p>
     *
     * @param fromObj
     * @param toObj
     * @param copyNull
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @author fanbeibei
     * @since 2015年2月11日
     */
    public static <T> void copyProperties(T fromObj, T toObj, boolean copyNull)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, SecurityException {
        if (null == fromObj || null == toObj) {
            throw new NullPointerException();
        }

        Method[] methods = fromObj.getClass().getMethods();

        Method setter = null;
        for (Method method : methods) {
            if (method.getName().length() > 3
                    && method.getName().startsWith("get")
                    && !method.getName().equals("getClass")) {
                Object value = method.invoke(fromObj, (Object[]) null);
                if (null == value && !copyNull) {
                    continue;
                } else {
                    try {
                        setter = toObj.getClass().getMethod(
                                "set" + method.getName().substring(3),
                                method.getReturnType());
                    }catch (NoSuchMethodException e){
                        continue;
                    }
                    setter.invoke(toObj, value);
                }
            }
        }

    }
}