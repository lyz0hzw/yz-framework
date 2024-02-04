package org.learn.framework.lowcode;

/**
 * 渲染内容接口
 */
public interface ContentRender {

    /**
     * 模板渲染
     * @param builder 渲染参数
     */
    void render(LowCodeBuilder builder);
}
