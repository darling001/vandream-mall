package com.vandream.mall.commons.utils;

import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.commons.annotation.FieldAlias;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/3/20
 * Time: 15:38
 * Description: 实体对象属性参数去空格
 */
public class ObjectUtil extends ObjectUtils {
    private static final String LIST_TYPE_NAME = "java.util.List";

    /**
     * 赋值对象属性 兼容{@code @FieldAlias}
     *
     * @param target
     * @param source
     * @param isExcludeStatic 是否排除静态字段
     * @param isExcludeFinal  是否排除final字段
     * @throws Exception
     */
    private static void copyProperties(Object target, Object source,
                                       boolean isExcludeStatic, boolean isExcludeFinal) throws
            Exception {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        Field[] targetFields = null;
        Field[] thisFields = targetClass.getDeclaredFields();
        // 获取父类属性
        Field[] superFields = targetClass.getSuperclass().getDeclaredFields();
        targetFields = new Field[thisFields.length + superFields.length];
        System.arraycopy(thisFields, 0, targetFields, 0, thisFields.length);
        System.arraycopy(superFields, 0, targetFields, thisFields.length, superFields.length);
        for (Field targetField : targetFields) {
            targetField.setAccessible(true);
            //是否排除静态字段
            if (isExcludeStatic) {
                if (Modifier.isStatic(targetField.getModifiers())) {
                    continue;
                }
            }
            //是否排除final字段
            if (isExcludeFinal) {
                if (Modifier.isFinal(targetField.getModifiers())) {
                    continue;
                }
            }
            String fieldName = null;
            //判断字段别名
            FieldAlias fieldAlias = targetField.getAnnotation(FieldAlias.class);
            if (fieldAlias == null) {
                fieldName = targetField.getName();
            } else {
                fieldName = fieldAlias.value();
            }
            //获取对应字段Field
            Field sourceFields = null;


            try {
                sourceFields = sourceClass.getDeclaredField(fieldName);

                sourceFields.setAccessible(true);
            } catch (Exception e) {
                try {
                    sourceFields = sourceClass.getSuperclass().getDeclaredField(fieldName);
                    sourceFields.setAccessible(true);
                } catch (Exception e1) {
                    continue;
                }
            }
            //赋值到目标字段
            Object sourceValue = sourceFields.get(source);

            if (isNotEmpty(sourceValue)) {
                //校验双方类型是否匹配
                String targetTypeName = targetField.getType().getTypeName();
                String sourceTypeName = sourceFields.getType().getTypeName();
                if (targetTypeName.equals(sourceTypeName)) {
                    if (LIST_TYPE_NAME.equals(sourceTypeName)) {
                        Class<?> collectionFieldArgsClass = getCollectionFieldArgsClass
                                (targetField);
                        if (isNotPrimitive(collectionFieldArgsClass)) {
                            List<?> transfer = transfer((List) sourceValue,
                                    collectionFieldArgsClass);
                            targetField.set(target, transfer);
                        } else {
                            targetField.set(target, sourceValue);
                        }
                    } else {
                        targetField.set(target, sourceValue);
                    }
                    targetField.set(target, sourceValue);
                }
            }

        }
    }

    /**
     * 基于JSON的对象转化
     *
     * @param target
     * @param source
     * @param isExcludeStatic
     * @param isExcludeFinal
     * @throws Exception
     */
    private static void transferByJson(Object target, Object source,
                        boolean isExcludeStatic, boolean isExcludeFinal) throws Exception {
        //获取FieldAlias 注解
        //JSON序列化source对象
        //

    }

    /**
     * 判断是否为基本类型 包括基本类型的封装类型
     *
     * @param clazz
     * @return Boolean
     * @see java.lang.String
     * @see java.lang.Boolean#TYPE
     * @see java.lang.Character#TYPE
     * @see java.lang.Byte#TYPE
     * @see java.lang.Short#TYPE
     * @see java.lang.Integer#TYPE
     * @see java.lang.Long#TYPE
     * @see java.lang.Float#TYPE
     * @see java.lang.Double#TYPE
     * @see java.lang.Void#TYPE
     */
    private static Boolean isPrimitive(Class<?> clazz) {
        if (clazz == null) {
            return false;
        } else {
            if (clazz.isPrimitive()) {
                return true;
            } else {
                return String.class.equals(clazz) || String[].class.equals(clazz) ||
                        Integer.class.equals(clazz) || Integer[].class.equals(clazz) ||
                        Long.class.equals(clazz) || Long[].class.equals(clazz) ||
                        Float.class.equals(clazz) || Float[].class.equals(clazz) ||
                        Double.class.equals(clazz) || Double[].class.equals(clazz) ||
                        Byte.class.equals(clazz) || Byte[].class.equals(clazz) ||
                        Boolean.class.equals(clazz) || Boolean[].class.equals(clazz) ||
                        Character.class.equals(clazz) || Character[].class.equals(clazz) ||
                        Short.class.equals(clazz) || Short[].class.equals(clazz) ||
                        Void.class.equals(clazz) || Void[].class.equals(clazz)
                        ;
            }
        }
    }

    private static Boolean isPrimitive(Field field) throws ClassNotFoundException {
        if (isEmpty(field)) {
            return false;
        } else {
            return isPrimitive(Class.forName(field.getType().getTypeName()));
        }
    }

    private static Boolean isNotPrimitive(Field field) throws ClassNotFoundException {
        return !isPrimitive(field);
    }

    /**
     * 判断是否为基本类型 包括基本类型的封装类型
     *
     * @param clazz
     * @return
     */
    private static Boolean isNotPrimitive(Class<?> clazz) {
        return !isPrimitive(clazz);
    }


    /**
     * POJO 对象转化 {@code List} 兼容{@code @FieldAlias}
     *
     * @param list  源对象List
     * @param clazz 目标对象class
     * @param <T>
     * @return
     * @throws SystemException
     */
    public static <T> List<T> transfer(List<?> list, Class<T> clazz) throws SystemException {
        try {
            List<T> resultList = new ArrayList<>();
            if (isEmpty(list)) {
                return resultList;
            }
            for (Object object : list) {
                T instance = transfer(object, clazz);
                resultList.add(instance);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(e.getMessage());
        }

    }

    /**
     * POJO 对象转化 兼容{@code @FieldAlias}
     *
     * @param object 源对象object
     * @param clazz  目标对象class
     * @param <T>
     * @return
     * @throws SystemException
     */
    public static <T> T transfer(Object object, Class<T> clazz) throws SystemException {
        try {
            if (isEmpty(object)) {
                return null;
            }
            T instance = clazz.newInstance();
            copyProperties(instance, object, true, true);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException();
        }

    }

    /**
     * 对象判空
     * <p>
     * <pre>
     *     ***********
     *     Object==null
     *     CharSequence.length==0
     *     Collection.isEmpty()==true
     *     Map.isEmpty()==true
     *     Array.Length()==0
     *     ***********
     *     return true
     * </pre>
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof CharSequence) {
            return StringUtil.isBlank((CharSequence)obj);
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }

        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    private static Class<?> getCollectionFieldArgsClass(Field collectionField) throws
            ClassNotFoundException {
        String className = "";
        ParameterizedType listGenericType = (ParameterizedType) collectionField.getGenericType();
        Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
        for (int i = 0; i < listActualTypeArguments.length; i++) {
            className = listActualTypeArguments[i].getTypeName();
        }
        Class<?> collectionClass = Class.forName(className);
        return collectionClass;
    }
}
