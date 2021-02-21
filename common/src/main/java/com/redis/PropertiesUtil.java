package com.redis;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties props;
    /**
     * 获取property key的值，如果值不存在，返回defaultValue
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(String key, String defaultValue) {
        ensureProps();
        String value = props.getProperty(key);
        if (value == null) {
            value = defaultValue;
        }

        return value;
    }

    /**
     * 获取一个Integer值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Long getLong(String key, Long defaultValue) {
        ensureProps();
        String value = props.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取一个Integer值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Integer getInteger(String key, Integer defaultValue) {
        ensureProps();
        String value = props.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取一个Boolean值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(String key, Boolean defaultValue) {
        ensureProps();
        String value = props.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void updateProperty(String key, String value) {
        ensureProps();
        props.setProperty(key, value);
    }

    private static void ensureProps() {
        if (props == null || props.isEmpty()) {
            props = new Properties();
            PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
            String[] resources = {"classpath:systemInfo.properties", "classpath:monitor.properties", "classpath:config/application.properties" };
            for(String resourcePath: resources) {
                try {
                    Resource resource = resourceResolver.getResource(resourcePath);
                    if(resource != null) {
                        loadProps(resource);
                    }
                } catch (IOException e) {
                    continue;
                }
            }
        }
    }

    private static void loadProps(Resource resource) throws IOException {
        InputStream in = resource.getInputStream();
        props.load(in);
    }

}

