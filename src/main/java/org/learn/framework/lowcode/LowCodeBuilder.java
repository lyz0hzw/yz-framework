package org.learn.framework.lowcode;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.caller.CallerUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;

import java.io.File;

/**
 * @author hongda.li 2022-04-06 13:33
 */
public class LowCodeBuilder {

    public static final String AUTHOR = "@YZ_AUTHOR";
    public static final String PACKAGE = "@YZ_PACKAGE";
    private String appName = "Application";
    private String rootPackage;
    private String rootPath;
    private String mainClassPath;
    private String configPath;
    private String controllerPath;
    private String entityPath;
    private String servicePath;
    private String serviceImplPath;
    private String daoPath;
    private String daoImplPath;
    private String dataBaseName = "demo";
    private String username = "root";
    private String password = "123456";
    private WebServer yzWebServer = WebServer.YzWebServer;
    private int port = 8088;
    private String author = "Application";

    public LowCodeBuilder setAuthor(String author){
        this.author = author;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public String getRootPackage() {
        return rootPackage;
    }

    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    public LowCodeBuilder setAppName(String name){
        this.appName = name;
        return this;
    }

    public LowCodeBuilder setDataBaseName(String dataBaseName){
        this.dataBaseName = dataBaseName;
        return this;
    }

    public LowCodeBuilder setUserName(String username){
        this.username = username;
        return this;
    }

    public LowCodeBuilder setPassword(String password){
        this.password = password;
        return this;
    }

    public LowCodeBuilder setWebServer(WebServer server){
        this.yzWebServer = server;
        return this;
    }

    public LowCodeBuilder setPort(int port){
        this.port = port;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getMainClassPath() {
        return mainClassPath;
    }

    public String getConfigPath() {
        return configPath;
    }

    public String getControllerPath() {
        return controllerPath;
    }

    public String getEntityPath() {
        return entityPath;
    }

    public String getServicePath() {
        return servicePath;
    }

    public String getServiceImplPath() {
        return serviceImplPath;
    }

    public String getDaoPath() {
        return daoPath;
    }

    public String getDaoImplPath() {
        return daoImplPath;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public WebServer getWebServer() {
        return yzWebServer;
    }

    public int getPort() {
        return port;
    }

    public static LowCodeBuilder build(){
        LowCodeBuilder builder = new LowCodeBuilder();
        builder.rootPackage = ClassUtil.getPackage(CallerUtil.getCallerCaller());
        builder.rootPath = getJavaFilePath(CallerUtil.getCallerCaller()) + "\\";
        builder.mainClassPath = builder.rootPath + builder.appName + ".java";
        builder.configPath = builder.rootPath + "config\\";
        new File(builder.configPath).mkdirs();
        builder.controllerPath = builder.rootPath + "controller\\";
        new File(builder.controllerPath).mkdirs();
        builder.entityPath = builder.rootPath + "entity\\";
        new File(builder.entityPath).mkdirs();
        builder.servicePath = builder.rootPath + "service\\";
        new File(builder.servicePath).mkdirs();
        builder.serviceImplPath = builder.servicePath + "serviceImpl\\";
        new File(builder.serviceImplPath).mkdirs();
        builder.daoPath = builder.rootPath + "dao\\";
        new File(builder.daoPath).mkdirs();
        builder.daoImplPath = builder.daoPath + "daoImpl\\";
        new File(builder.daoImplPath).mkdirs();
        return builder;
    }

    public void render(){
        // 扫描数据包下实现了ContentRender的类并且该类不是接口，执行该类的render方法进行渲染
        ClassUtil.scanPackage("org.learn.framework.lowcode", clazz -> ContentRender.class.isAssignableFrom(clazz) && !clazz.isInterface()).forEach(clazz -> {
            ReflectUtil.invoke(ReflectUtil.newInstance(clazz), ReflectUtil.getMethodByName(clazz,"render"), this);
        });
    }

    public static void writeContent(String path, String content){
        new FileWriter(path).write(content);
    }

    public static String getJavaFilePath(Class<?> clazz){
        String packageName = ClassUtil.getPackagePath(clazz);
        String projectPath = new File("").getAbsolutePath();
        String formatPackageName = packageName.replace(".", "/");
        // 两种不同的项目文件构成格式
        if (new File("pom.xml").exists()){
            return new File(projectPath
                    + "/src/main/java/"
                    + formatPackageName
                    + "/").getAbsolutePath();
        }else {
            return new File(projectPath
                    + "/src"
                    + formatPackageName
                    + "/").getAbsolutePath();
        }
    }
}
