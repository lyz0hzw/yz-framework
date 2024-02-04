package org.learn.framework.lowcode;

/**
 * 主类渲染
 */
public class MainClassRender implements ContentRender{

    private static final String CONTENT = "package @YZ_PACKAGE;\n" +
            "\n" +
            "import org.learn.framework.Starter;\n" +
            "import org.learn.framework.annotation.BootApplication;\n" +
            "\n" +
            "/**\n" +
            " * @author @YZ_AUTHOR \n" +
            " */\n" +
            "@BootApplication\n" +
            "public class Application {\n" +
            "\n" +
            "    public static void main(String[] args) {\n" +
            "        // TODO before run\n" +
            "        Starter.run(Application.class);\n" +
            "        // TODO after run;\n" +
            "    }\n" +
            "}\n";

    @Override
    public void render(LowCodeBuilder builder) {
        String replace = CONTENT
                .replace(LowCodeBuilder.AUTHOR, builder.getAuthor())
                .replace(LowCodeBuilder.PACKAGE, builder.getRootPackage());
        LowCodeBuilder.writeContent(builder.getMainClassPath(), replace);
    }
}
