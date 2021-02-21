package com.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class ConvertUtil {

    /**
     * 通用DO/DTO转换的方法
     *
     * @param source 实例对象
     * @param target 要装换的class
     * @param <T>    泛型
     * @return 转换的实例对象
     */
    public static <T> T convert(Object source, Class<T> target) {
        try {
            T result = target.newInstance();
            BeanUtils.copyProperties(source, result);
            return result;
        } catch (Exception e) {
            log.error("转换异常", e);
            return null;
        }
    }

    public static <T> String getFieldWhenNotEmpty(T object, Function<T, ? extends String> function) {
        return Optional.ofNullable(object).map(function).orElse(null);
    }

}
