package org.learn.test.lowcode;

import org.learn.framework.lowcode.LowCodeBuilder;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2024/1/19 17:14
 * @title lowcodeTest
 * @description <TODO description class purpose>
 */

public class lowcodeTest {
    public static void main(String[] args) {
        LowCodeBuilder
                .build() // 构建项目结构
                .setDataBaseName("epm-corpus")
                .render(); // 渲染类文件
    }
}
