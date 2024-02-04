package org.learn.framework.lowcode;

/**
 * @author hongda.li 2022-04-06 14:10
 */
public class DataBaseConfigRender implements ContentRender{

    public static final String USERNAME = "@YZ_USERNAME";

    public static final String PASSWORD = "@YZ_PASSWORD";

    public static final String DATABASE = "@YZ_DATA_BASE_NAME";

    private static final String CONTENT = "package @YZ_PACKAGE.config;\n" +
            "\n" +
            "import org.learn.framework.annotation.Bean;\n" +
            "import org.learn.framework.annotation.Configuration;\n" +
            "import org.learn.framework.config.DataConfig;\n" +
            "\n" +
            "/**\n" +
            " * @author @YZ_AUTHOR\n" +
            " */\n" +
            "@Configuration\n" +
            "public class DataBaseConfiguration {\n" +
            "    \n" +
            "    @Bean\n" +
            "    public DataConfig config(){\n" +
            "        DataConfig config = new DataConfig();\n" +
            "        config.setUserName(\"@YZ_USERNAME\");\n" +
            "        config.setPassWord(\"@YZ_PASSWORD\");\n" +
            "        config.setConnectionUrl(\"jdbc:mysql://localhost:3306/@YZ_DATA_BASE_NAME?useSSL=false\");\n" +
            "        return config;\n" +
            "    }\n" +
            "}";

    @Override
    public void render(LowCodeBuilder builder) {
        String replace = CONTENT
                .replace(LowCodeBuilder.PACKAGE, builder.getRootPackage())
                .replace(LowCodeBuilder.AUTHOR, builder.getAuthor())
                .replace(DATABASE, builder.getDataBaseName())
                .replace(USERNAME, builder.getUsername())
                .replace(PASSWORD, builder.getPassword());
        LowCodeBuilder.writeContent(builder.getConfigPath() + "DataBaseConfiguration.java", replace);
    }
}
