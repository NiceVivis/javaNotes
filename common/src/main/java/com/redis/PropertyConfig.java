package com.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * property配置文件load基类
 */
public class PropertyConfig {
    private final static Logger logger = LoggerFactory.getLogger(PropertyConfig.class);

    private Properties props = new Properties();

    private String propertyFile;

    private Map<String, String> propsMap = new HashMap<String, String>();

    public PropertyConfig(String propertyFile) {
        this.propertyFile = propertyFile;
        load();
    }

    private void load() {
        if (propertyFile == null) {
            return;
        }

        try {
            InputStream input;
            if (propertyFile.indexOf("http:") == -1) {
                input = TraceUtils.openResourceInputStream(propertyFile);
            } else {
                URL url = new URL(propertyFile);
                input = url.openStream();

            }
            //			System.out.println("start to load property file " + propertyFile);

            if (input == null) {
                return;
            }
            this.props.load(input);

            if (props != null) {
                Properties newProps = new Properties();
                for (Object key : props.keySet()) {
                    if (key == null) {
                        continue;
                    }
                    String keyLower = key.toString().toLowerCase();
                    Object value = props.get(key);
                    newProps.put(keyLower, value);
                    newProps.put(key, value);
                }
                props.clear();
                props.putAll(newProps);
            }

            // 转换Properties成Map
            convert2Map();

            input.close();
        } catch (Exception e) {
            logger.warn("property file " + propertyFile
                    + " does not exist in classpath");
            logger.warn("", e);
        }
    }

    /**
     * 获取key的属性值
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        //return props.getProperty(key);
        return getValue(key, null);
    }

    /**
     * 获取property key的值，如果值不存在，返回defaultValue
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getValue(String key, String defaultValue) {
        //String value = props.getProperty(key);
        String value = propsMap.get(key);
        if (value == null) {
            value = propsMap.get(key.toLowerCase());
        }
        if (value == null) {
            value = defaultValue;
        }

        return value;
    }

    @SuppressWarnings("all")
    private void convert2Map() {
        if (props.size() > 0) {
            Map<String, String> tempMap = new HashMap<String, String>((Map) props);
            propsMap = tempMap;
        }
    }
}

