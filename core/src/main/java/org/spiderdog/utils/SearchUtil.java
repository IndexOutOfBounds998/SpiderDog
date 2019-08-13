package org.spiderdog.utils;

/**
 * Created by yang on 2019/8/13.
 */

import org.spiderdog.annotation.Field;
import org.spiderdog.annotation.Source;
import org.spiderdog.model.Rule;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchUtil
 */
public class SearchUtil {


    public static <T> String getUrl(T model) {
        return getUrl(model.getClass());
    }

    public static String getUrl(Class clazz) {
        return ((Source) clazz.getAnnotation(Source.class)).url();
    }

    public static <T> HashMap<String, Rule> getFieldAnno(T model) {
        return getFieldAnno(model.getClass());
    }


    public static HashMap<String, Rule> getFieldAnno(Class clazz) {
        HashMap<String, Rule> fieldsMap = new HashMap<>();
        java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            Type genericType = field.getGenericType();
            if (field.isAnnotationPresent(Field.class)) {
                String name = field.getName();
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    Field field1 = (Field) annotation;
                    Rule rule = new Rule();
                    rule.setSeletor(field1.selector());
                    rule.setType(genericType);
                    rule.setAttr(field1.attr());
                    rule.setNextUrl(field1.isNextUrl());
                    fieldsMap.put(name, rule);
                }
            }
        }
        return fieldsMap;
    }


    /**
     * 将传入的value字符串对象转换为type对应的具体子类类型对象（返回时会向上造型为Object对象） 注：只接受String及基本类型及对应包装类的转换
     */
    private static Object convert(Class type, String value) {
        if (type.equals(String.class)) {
            return value;
        }
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(value);
        }
        if (type.equals(Short.class) || type.equals(short.class)) {
            if (value == null) {
                return 0;
            }
            return Short.parseShort(value);
        }
        if (type.equals(Integer.class) || type.equals(int.class)) {
            if (value == null) {
                return 0;
            }
            return Integer.parseInt(value);
        }
        if (type.equals(Float.class) || type.equals(float.class)) {
            if (value == null) {
                return 0f;
            }
            return Float.parseFloat(value);
        }
        if (type.equals(Double.class) || type.equals(double.class)) {
            if (value == null) {
                return 0.0;
            }
            return Double.parseDouble(value);
        }
        if (type.equals(Long.class) || type.equals(long.class)) {
            if (value == null) {
                return 0l;
            }
            return Long.parseLong(value);
        }
        if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            if (value == null) {
                return false;
            }
            return Boolean.parseBoolean(value);
        }
        return null;

    }

    /* 将map对象转换为对应的实体类对象 */
    public static <K, V, T> T MapToModel(Map<K, V> map, Class<T> clazz) {
        Method[] methods = clazz.getMethods();
        // Set<Method> methodsSet = new HashSet<Method>();
        Map<String, Method> methodsMap = new HashMap<String, Method>();
        for (Method method : methods) {
            // 添加所有Setter方法
            if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
                // methodsSet.add(method);
                methodsMap.put(method.getName(), method);
            }
        }
        if (map == null)
            return null;
        T model = null;
        try {
            model = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (K key : map.keySet()) {
            // System.out.println(key + "：" + map.get(key));
            String field = (String) key;

            Object value = map.get(key);
            // 拼装Setter方法名
            String firstLetter = field.substring(0, 1).toUpperCase();
            String methodName = "set" + firstLetter + field.substring(1);
            // System.out.println(methodName);
            try {
                // Method method = clazz.getDeclaredMethod(methodName,String.class);
                // method.invoke(model,value);
                Method method = methodsMap.get(methodName);
                if (method == null) {
                    throw new Exception("没有" + method.getName() + "方法，请检查相应实体类(可能的问题：ES中新增了字段，但在实体类中没有定义)！");
                }
                Class parameterType = method.getParameterTypes()[0];// 获取Setter的参数类型
                // 根据参数类型对value(String类型)进行类型转换（转换为具体的子类类型）
                Object value2 = convert(parameterType, value.toString());
                // 执行invoke方法，传入的参数类型必须匹配（第一个参数），同时传入的值value（第二个参数）具体的子类类型必须匹配
                // value可以进行向上造型操作，只要保证子类类型一致即可
                method.invoke(model, convert(parameterType, value2.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return model;
    }


}