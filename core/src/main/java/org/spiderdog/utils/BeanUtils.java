package org.spiderdog.utils;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * Created by yang on 2019/8/13.
 */
public class BeanUtils {

    /**
     * Map转实体类
     *
     * @param map    需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
     * @param entity 需要转化成的实体类
     * @return
     */
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        T t = null;
        try {
            t = entity.newInstance();
            for (java.lang.reflect.Field field : entity.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object != null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (InstantiationException | IllegalAccessException e) {

            e.printStackTrace();
        }
        return t;
    }


    /* 将实体类对象转换为Json字符串 */
    public static <T> String ModelToJson(T model) {
        // 直接调用fastjson 转成json字符串
        return JSON.toJSONString(model, true);
    }

}
