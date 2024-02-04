package org.learn.framework.lowcode;

/**
 * WebServer配置类
 */
public class WebServerConfigRender implements ContentRender{

    public static final String PORT = "@YZ_PORT";

    private static final String CONTENT = "package @YZ_PACKAGE.config;\n" +
            "\n" +
            "import org.learn.framework.annotation.Bean;\n" +
            "import org.learn.framework.annotation.Configuration;\n" +
            "import org.learn.framework.config.WebConfig;\n" +
            "\n" +
            "/**\n" +
            " * @author @YZ_AUTHOR \n" +
            " */\n" +
            "@Configuration\n" +
            "public class WebServerConfiguration {\n" +
            "    \n" +
            "    @Bean\n" +
            "    public WebConfig config(){\n" +
            "        WebConfig config = new WebConfig();\n" +
            "        config.setPort(@YZ_PORT);\n" +
            "        return config;\n" +
            "    }\n" +
            "}\n";

    @Override
    public void render(LowCodeBuilder builder) {
        String replace = CONTENT
                .replace(LowCodeBuilder.PACKAGE, builder.getRootPackage())
                .replace(LowCodeBuilder.AUTHOR, builder.getAuthor())
                .replace(PORT, String.valueOf(builder.getPort()));
        LowCodeBuilder.writeContent(builder.getConfigPath() + "WebServerConfiguration.java", replace);

    }
}
