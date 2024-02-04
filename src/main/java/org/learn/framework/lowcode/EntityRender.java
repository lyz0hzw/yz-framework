package org.learn.framework.lowcode;


import cn.hutool.core.util.StrUtil;
import org.learn.framework.log.YzStaticLog;
import org.learn.framework.util.CollectionUtil;

import java.util.List;
import java.util.Map;

/**
 * @author hongda.li 2022-04-06 14:53
 */
public class EntityRender implements ContentRender {

    public static final String GET_METHOD = "@YZ_GET_METHOD";

    public static final String SET_METHOD = "@YZ_SET_METHOD";

    public static final String FIELD = "@YZ_FIELD";

    public static final String BEAN = "@YZ_BEAN";

    private static final String CONTENT = "package @YZ_PACKAGE.entity;\n" +
            "\n" +
            "/**\n" +
            " * @author @YZ_AUTHOR\n" +
            " */\n" +
            "public class @YZ_BEAN {\n" +
            "    \n" +
            "@YZ_FIELD" +
            "@YZ_GET_METHOD" +
            "@YZ_SET_METHOD" +
            "}";

    @Override
    public void render(LowCodeBuilder builder) {
        Map<String, List<CollectionUtil.Binary<String, String>>> metaData = MetaDataBuilder.getMetaData(builder);
        String replace = CONTENT
                .replace(LowCodeBuilder.PACKAGE, builder.getRootPackage())
                .replace(LowCodeBuilder.AUTHOR, builder.getAuthor());
        metaData.forEach((key, value) -> {
            StringBuilder fields = new StringBuilder();
            StringBuilder setMethods = new StringBuilder();
            StringBuilder getMethods = new StringBuilder();
            for (CollectionUtil.Binary<String, String> column : value) {
                String fieldName = column.getX();
                // 如果是下面几种数据类型，则添加响应的基础数据类型结构
                if ("int".equalsIgnoreCase(column.getY())
                        || "bigint".equalsIgnoreCase(column.getY())
                        || "smallint".equalsIgnoreCase(column.getY())
                        || "tiny".equalsIgnoreCase(column.getY())){
                    fields.append("    private int ").append(fieldName).append(";").append("\n\n");
                    // 生成相应字段的get和set方法
                    setMethods.append("    public void set")
                            .append(StrUtil.upperFirst(fieldName))
                            .append("(")
                            .append("int ")
                            .append(fieldName)
                            .append("){\n")
                            .append("        this.")
                            .append(fieldName)
                            .append("=")
                            .append(fieldName)
                            .append(";")
                            .append("\n")
                            .append("    }\n\n");
                    getMethods.append("    public int get")
                            .append(StrUtil.upperFirst(fieldName))
                            .append("(){\n")
                            .append("        return this.")
                            .append(fieldName)
                            .append(";")
                            .append("\n")
                            .append("    }\n\n");
                }else {
                    fields.append("    private String ").append(fieldName).append(";").append("\n\n");
                    setMethods.append("    public void set")
                            .append(StrUtil.upperFirst(fieldName))
                            .append("(")
                            .append("String ")
                            .append(fieldName)
                            .append("){\n")
                            .append("        this.")
                            .append(fieldName)
                            .append("=")
                            .append(fieldName)
                            .append(";")
                            .append("\n")
                            .append("    }\n\n");
                    getMethods.append("    public String get")
                            .append(StrUtil.upperFirst(fieldName))
                            .append("(){\n")
                            .append("        return this.")
                            .append(fieldName)
                            .append(";")
                            .append("\n")
                            .append("    }\n\n");
                }
            }
            String replaceField = replace.replace(BEAN, StrUtil.upperFirst(key)).replace(FIELD, fields.toString()).replace(SET_METHOD, setMethods.toString()).replace(GET_METHOD, getMethods.toString());
            YzStaticLog.log(EntityRender.class.getName(), "开始生成实体类[{}]", StrUtil.upperFirst(key));
            LowCodeBuilder.writeContent(builder.getEntityPath() + StrUtil.upperFirst(key) + ".java", replaceField);
        });
    }
}
