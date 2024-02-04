package org.learn.framework.web;


import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import org.learn.framework.annotation.*;
import org.learn.framework.context.ContextContainer;
import org.learn.framework.context.YzClass;
import org.learn.framework.util.ClassUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author yixi
 */
public class RequestBody {

    private final Map<String, YzClass> REQUEST_BODY = new HashMap<>();

    private final Map<String, RequestMethod[]> REQUEST_METHOD = new HashMap<>();

    public RequestBody(){
        init();
    }

    public Set<String> getMapping() {
        return REQUEST_BODY.keySet();
    }

    public YzClass getClassAndMethod(String mapping) {
        String match = match(mapping);
        if ("".equals(match)){
            return null;
        }
        return REQUEST_BODY.get(match);
    }

    public RequestMethod[] getRequestMethod(String mapping){
        String match = match(mapping);
        if ("".equals(match)){
            return null;
        }
        return REQUEST_METHOD.get(match);
    }

    /**
     * 设置 url 到 类的方法 的映射
     */
    public void setMapping(String mapping, YzClass yzClass) {
        this.REQUEST_BODY.put(mapping, yzClass);
    }

    public void setMapping(String mapping, RequestMethod[] method){
        this.REQUEST_METHOD.put(mapping, method);
    }

    public String match(String requestUri){
        for (String api : getMapping()) {
            if (api.equals(requestUri)){
                return api;
            }
            if (api.contains("{") && api.contains("}") && ReUtil.isMatch(formatUrl(api), requestUri)) {
                return api;
            }
        }
        return "";
    }

    private String formatUrl(String url) {
        String originPattern = url.replaceAll("(\\{\\w+})", "[\\\\u4e00-\\\\u9fa5_a-zA-Z0-9]+");
        String pattern = "^" + originPattern + "/?$";
        return pattern.replaceAll("/+", "/");
    }

    private void init(){
        Log log = Log.get();
        // 遍历带有@Controller注解的类
        ClassUtils.getClassesWithAnnotation(Controller.class).forEach(clazz -> {
            RequestMapping apiOnClass = clazz.getAnnotation(RequestMapping.class);
            String mappingOnClass = apiOnClass != null ? "".equals(apiOnClass.value()) ? "/" + clazz.getSimpleName() : StrUtil.addPrefixIfNot(apiOnClass.value(),"/") : "";
            // ========================================处理@RequestMapping===============================================
            Arrays.asList(ReflectUtil.getMethods(clazz, method -> method.getAnnotation(RequestMapping.class) != null)).forEach(method -> {
                if (ContextContainer.getApplicationContext().containsBean(clazz)) { // 遍历controller中带有requestmapping的方法
                    RequestMapping apiOnMethod = method.getAnnotation(RequestMapping.class);
                    String mappingOnMethod = "".equals(apiOnMethod.value()) ? "/" + method.getName() : StrUtil.addPrefixIfNot(apiOnMethod.value(),"/");
                    YzClass yzClass = new YzClass();
                    yzClass.setMethod(method);
                    yzClass.setClazz(clazz);
                    if (apiOnClass == null){
                        this.setMapping(mappingOnMethod, yzClass);
                        this.setMapping(mappingOnMethod, apiOnMethod.methods());
                        log.info("Api设置成功[{}][{}][{}][{}]",mappingOnMethod,apiOnMethod.methods(),clazz.getName(),method.getName());
                    }else {
                        this.setMapping(mappingOnClass + mappingOnMethod, yzClass);
                        this.setMapping(mappingOnClass + mappingOnMethod, apiOnMethod.methods());
                        log.info("Api设置成功[{}][{}][{}][{}]",mappingOnClass + mappingOnMethod,apiOnMethod.methods(),clazz.getName(),method.getName());
                    }
                }
            });
            // ===========================================处理@GetMapping================================================
            Arrays.asList(ReflectUtil.getMethods(clazz, method -> method.getAnnotation(GetMapping.class) != null)).forEach(method -> {
                if (ContextContainer.getApplicationContext().containsBean(clazz)) {
                    GetMapping apiOnMethod = method.getAnnotation(GetMapping.class);
                    String mappingOnMethod = "".equals(apiOnMethod.value()) ? "/" + method.getName() : StrUtil.addPrefixIfNot(apiOnMethod.value(),"/");
                    YzClass yzClass = new YzClass();
                    yzClass.setMethod(method);
                    yzClass.setClazz(clazz);
                    if (apiOnClass == null){
                        this.setMapping(mappingOnMethod, yzClass);
                        this.setMapping(mappingOnMethod, new RequestMethod[]{RequestMethod.GET});
                        log.info("Api设置成功[{}][{}][{}][{}]",mappingOnMethod,RequestMethod.GET.getValue(),clazz.getName(),method.getName());
                    }else {
                        this.setMapping(mappingOnClass + mappingOnMethod, yzClass);
                        this.setMapping(mappingOnClass + mappingOnMethod, new RequestMethod[]{RequestMethod.GET});
                        log.info("Api设置成功[{}][{}][{}][{}]",mappingOnClass + mappingOnMethod,RequestMethod.GET.getValue(),clazz.getName(),method.getName());
                    }
                }
            });
            // ============================================处理@PostMapping==============================================
            Arrays.asList(ReflectUtil.getMethods(clazz, method -> method.getAnnotation(PostMapping.class) != null)).forEach(method -> {
                if (ContextContainer.getApplicationContext().containsBean(clazz)) {
                    PostMapping apiOnMethod = method.getAnnotation(PostMapping.class);
                    String mappingOnMethod = "".equals(apiOnMethod.value()) ? "/" + method.getName() : StrUtil.addPrefixIfNot(apiOnMethod.value(),"/");
                    YzClass yzClass = new YzClass();
                    yzClass.setMethod(method);
                    yzClass.setClazz(clazz);
                    if (apiOnClass == null){
                        this.setMapping(mappingOnMethod, yzClass);
                        this.setMapping(mappingOnMethod, new RequestMethod[]{RequestMethod.POST});
                        log.info("Api设置成功[{}][{}][{}][{}]",mappingOnMethod,RequestMethod.POST.getValue(),clazz.getName(),method.getName());
                    }else {
                        this.setMapping(mappingOnClass + mappingOnMethod, yzClass);
                        this.setMapping(mappingOnClass + mappingOnMethod, new RequestMethod[]{RequestMethod.POST});
                        log.info("Api设置成功[{}][{}][{}][{}]",mappingOnClass + mappingOnMethod,RequestMethod.POST.getValue(),clazz.getName(),method.getName());
                    }
                }
            });
            // =============================================处理@DeleteMapping===========================================
            Arrays.asList(ReflectUtil.getMethods(clazz, method -> method.getAnnotation(DeleteMapping.class) != null)).forEach(method -> {
                if (ContextContainer.getApplicationContext().containsBean(clazz)) {
                    DeleteMapping apiOnMethod = method.getAnnotation(DeleteMapping.class);
                    String mappingOnMethod = "".equals(apiOnMethod.value()) ? "/" + method.getName() : StrUtil.addPrefixIfNot(apiOnMethod.value(),"/");
                    YzClass yzClass = new YzClass();
                    yzClass.setMethod(method);
                    yzClass.setClazz(clazz);
                    if (apiOnClass == null){
                        this.setMapping(mappingOnMethod, yzClass);
                        this.setMapping(mappingOnMethod, new RequestMethod[]{RequestMethod.DELETE});
                        log.info("Api设置成功[{}][{}][{}][{}]",mappingOnMethod,RequestMethod.DELETE.getValue(),clazz.getName(),method.getName());
                    }else {
                        this.setMapping(mappingOnClass + mappingOnMethod, yzClass);
                        this.setMapping(mappingOnClass + mappingOnMethod, new RequestMethod[]{RequestMethod.DELETE});
                        log.info("Api设置成功[{}][{}][{}][{}]",mappingOnClass + mappingOnMethod,RequestMethod.DELETE.getValue(),clazz.getName(),method.getName());
                    }
                }
            });
            // ============================================处理@PutMapping===============================================
            Arrays.asList(ReflectUtil.getMethods(clazz, method -> method.getAnnotation(PutMapping.class) != null)).forEach(method -> {
                if (ContextContainer.getApplicationContext().containsBean(clazz)) {
                    PutMapping apiOnMethod = method.getAnnotation(PutMapping.class);
                    String mappingOnMethod = "".equals(apiOnMethod.value()) ? "/" + method.getName() : StrUtil.addPrefixIfNot(apiOnMethod.value(),"/");
                    YzClass yzClass = new YzClass();
                    yzClass.setMethod(method);
                    yzClass.setClazz(clazz);
                    if (apiOnClass == null){
                        this.setMapping(mappingOnMethod, yzClass);
                        this.setMapping(mappingOnMethod, new RequestMethod[]{RequestMethod.PUT});
                        log.info("Api设置成功[{}][{}][{}][{}]",mappingOnMethod,RequestMethod.PUT.getValue(),clazz.getName(),method.getName());
                    }else {
                        this.setMapping(mappingOnClass + mappingOnMethod, yzClass);
                        this.setMapping(mappingOnClass + mappingOnMethod, new RequestMethod[]{RequestMethod.PUT});
                        log.info("Api设置成功[{}][{}][{}][{}]",mappingOnClass + mappingOnMethod,RequestMethod.PUT.getValue(),clazz.getName(),method.getName());
                    }
                }
            });
        });
    }

}
