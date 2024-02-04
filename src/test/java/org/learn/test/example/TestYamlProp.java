package org.learn.test.example;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.setting.yaml.YamlUtil;
import org.learn.framework.config.YamlProp;

import java.nio.charset.StandardCharsets;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2023/9/11 19:50
 * @title TestYamlProp
 * @description <TODO description class purpose>
 */

public class TestYamlProp {
    public static void main(String[] args) {
        YamlProp yamlProp = new YamlProp("test.yaml");
        System.out.println(yamlProp.get("server.port"));

        Dict load = YamlUtil.load(ResourceUtil.getReader("test.yaml", StandardCharsets.UTF_8));
        System.out.println(load.getByPath("server.port", Object.class));
        System.out.println(load.getByPath("spring.datasource.driverClassName", Object.class));

    }
}
