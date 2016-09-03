package com.synaric.app.rxmodel.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * Created by Synaric on 2016/8/30 0030.
 */
public class ReflectUtils {

    /**
     * 获取指定类的第position个位置参数。
     * @param clz 需要获取确切类型的类。
     * @param index 需要获取确切类型的泛型的索引，从0开始。
     * @return 确切类型。
     */
    public static Class<?> getActualClass(Class<?> clz, int index) {
        Type genType = clz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
