package org.learn.framework.config;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.setting.yaml.YamlUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * yaml配置映射文件
 */
public class YamlProp {
    private LinkedHashMap<String, Object> params;
    private final Map<String, Object> props = new HashMap<>();

    public YamlProp(){}

    public YamlProp(String yaml){
        YamlUtil.load(ResourceUtil.getReader(yaml, StandardCharsets.UTF_8));
        this.params = YamlUtil.load(ResourceUtil.getStream(yaml), LinkedHashMap.class);
    }

    public Object get(String key) {
        if (this.props.containsKey(key)){
            return this.props.get(key);
        }
        if (!key.contains(".")){
            return this.params.get(key);
        }
        String[] keys = key.split("\\.");
        int count = keys.length;
        Map<String, Object> temp = params;
        for (int i = 0; i < count; i++) {
            if (i == count - 1){
                Object value = temp.get(keys[i]);
                this.props.put(key, value);
                return value;
            }
            temp = (Map<String, Object>) temp.get(keys[i]);
        }
        return null;
    }

    public void put(String key, Object value){
        this.props.put(key, value);
    }
}
