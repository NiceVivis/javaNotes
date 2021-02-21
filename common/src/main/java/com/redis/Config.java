package com.redis;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class Config {
    private static ConfigurableEnvironment configurableEnvironment;
    /**
     * 获取key的属性值
     * @param key
     * @return
     */
    public static String getValue(String key){
        ensureProps();
        return Config.configurableEnvironment.getProperty(key);
    }

    /**
     * 获取key的属性值
     * @param <T>
     * @param key
     * @return
     */
    public static <T> T getValue(String key, Class<T> clazz){
        String k = getValue(key);
        if(k == null){
            k = getValue(key.toLowerCase());
        }
        return ObjectUtil.convertValue(k, clazz);
    }



    /**
     * 获取property key的值，如果值不存在，返回defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getValue(String key, String defaultValue){
        String value = getValue(key);
        if(value == null){
            value = getValue(key.toLowerCase());
        }
        return value == null ? defaultValue : value;
    }

    /**
     * 获取key的属性值
     * @param <T>
     * @param key
     * @return
     */
    public static <T> T getValue(String key, Class<T> clazz, T defaultValue){
        T obj = getValue(key, clazz);
        return obj == null ? defaultValue : obj;
    }


    /**
     * Don't change any global configured properties in runtime
     * But it still supported.
     * @param key
     * @param value
     * @author wangpeng04
     * @deprecated since 1.6.2, see the method description
     */
    @Deprecated
    public static void updateProperty(String key,String value) {
        ensureProps();
        MutablePropertySources propertySources =
                Config.configurableEnvironment.getPropertySources();
        String sourceName = "OptimusDynamicPropertySource";
        PropertiesPropertySource optimusDynamicPropertySource;
        Map<String, Object> props;
        if(propertySources.contains(sourceName)) {
            optimusDynamicPropertySource = (PropertiesPropertySource)propertySources.get(sourceName);
            props = optimusDynamicPropertySource.getSource();
        } else {
            optimusDynamicPropertySource = new PropertiesPropertySource(sourceName, new Properties());
            props = optimusDynamicPropertySource.getSource();
            propertySources.addLast(optimusDynamicPropertySource);
        }
        props.put(key, value);
        props.put(key.toLowerCase(), value);

    }
    @Deprecated
    public void setPropertyConfigurer(PropertyPlaceholderConfigurer propertyConfigurer) {

    }
    public static void setEnvironment(ConfigurableEnvironment configurableEnvironment) {
        Config.configurableEnvironment = configurableEnvironment;
    }

    /**
     * Fallback method
     */
    @Deprecated
    private static void ensureProps() {
        if(configurableEnvironment == null) {
            synchronized (Config.class) {
                if (configurableEnvironment == null) {
                    configurableEnvironment = new StandardEnvironment();
                    Properties props;
                    PropertyPlaceholderConfigurer propertyConfigurer = new PropertyPlaceholderConfigurer();
                    PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
                    try {
                        Resource[] locations = resourceResolver.getResources("classpath:**/*.properties");
                        propertyConfigurer.setLocations(locations);
                        propertyConfigurer.setFileEncoding("utf-8");
                        props = (Properties) CommonUtil.invokeMethod(propertyConfigurer, "mergeProperties");
                        if(props != null){
                            Properties newProps = new Properties();
                            for(Object key : props.keySet()){
                                if(key == null) {
                                    continue;
                                }
                                String keyLower = key.toString().toLowerCase();
                                Object value = props.get(key);
                                if (value == null) {
                                    continue;
                                }
                                newProps.put(keyLower, value);
                                newProps.put(key, value);
                            }
                            props.clear();
                            props.putAll(newProps);
                            PropertiesPropertySource optimusPropertiesSource = new PropertiesPropertySource("OptimusPropertiesSource", props);
                            configurableEnvironment.getPropertySources().addFirst(optimusPropertiesSource);
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
