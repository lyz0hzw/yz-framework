package org.learn.framework.lowcode;

import cn.hutool.core.util.StrUtil;
import org.learn.framework.log.YzStaticLog;
import org.learn.framework.util.CollectionUtil;

import java.util.List;
import java.util.Map;

/**
 * Controller 渲染
 */
public class ControllerRender implements ContentRender{

    public static final String ENTITY = "@YZ_ENTITY";

    private static final String CONTENT = "package @YZ_PACKAGE.controller;\n" +
            "\n" +
            "import @YZ_PACKAGE.entity.@YZ_BEAN;\n" +
            "import org.learn.framework.annotation.*;\n" +
            "import org.learn.framework.web.ResponseBody;\n" +
            "\n" +
            "/**\n" +
            " * @author @YZ_AUTHOR\n" +
            " */\n" +
            "@Controller\n" +
            "public class @YZ_BEANController {\n" +
            "\n" +
            "    @PostMapping(\"/@YZ_ENTITY\")\n" +
            "    public ResponseBody insert(@YZ_BEAN @YZ_ENTITY){\n" +
            "        return ResponseBody.success();\n" +
            "    }\n" +
            "\n" +
            "    @DeleteMapping(\"/@YZ_ENTITY\")\n" +
            "    public ResponseBody delete(int id){\n" +
            "        return ResponseBody.success();\n" +
            "    }\n" +
            "\n" +
            "    @PutMapping(\"/@YZ_ENTITY\")\n" +
            "    public ResponseBody update(@YZ_BEAN @YZ_ENTITY){\n" +
            "        return ResponseBody.success();\n" +
            "    }\n" +
            "\n" +
            "    @GetMapping(\"/@YZ_ENTITY\")\n" +
            "    public ResponseBody findAll(){\n" +
            "        return ResponseBody.success();\n" +
            "    }\n" +
            "\n" +
            "    @GetMapping(\"/@YZ_ENTITY/{id}\")\n" +
            "    public ResponseBody find(int id){\n" +
            "        return ResponseBody.success();\n" +
            "    }\n" +
            "}\n";


    @Override
    public void render(LowCodeBuilder builder) {
        Map<String, List<CollectionUtil.Binary<String, String>>> metaData = MetaDataBuilder.getMetaData(builder);
        String replace = CONTENT
                .replace(LowCodeBuilder.PACKAGE, builder.getRootPackage())
                .replace(LowCodeBuilder.AUTHOR, builder.getAuthor());
        metaData.forEach((key, value) -> {
            String replaceController = replace
                    .replace(EntityRender.BEAN, StrUtil.upperFirst(key)).replace(ENTITY, key);
            YzStaticLog.log(ContentRender.class.getName(),"开始生成Controller[{}Controller]", StrUtil.upperFirst(key));
            LowCodeBuilder.writeContent(builder.getControllerPath() + StrUtil.upperFirst(key) + "Controller.java", replaceController);
        });
    }
}
