package org.learn.framework.permission;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.NoneJWTSigner;
import org.learn.framework.config.PermissionConfig;
import org.learn.framework.context.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Token管理器
 */
public class TokenManager {

    private static final String TOKEN_NAME = "yz_token";

    private static final String CREATE_TIME = "create_time";

    private static final String EXPIRE_TIME = "expire_time";

    /**
     * 创建token
     * @param message 携带信息
     * @return token值
     */
    public static String createToken(String message){
        Map<String, Object> params = new HashMap<>(2);
        params.put(TOKEN_NAME, message);
        return createToken(params);
    }

    /**
     * 创建token，添加创建时间和过期时间在token中
     * @param params 参数
     * @return token值
     */
    public static String createToken(Map<String, Object> params){
        int tokenTime = Environment.getConfig(PermissionConfig.class).getTokenTime();
        params.put(CREATE_TIME, Environment.getSystemTime());
        params.put(EXPIRE_TIME, tokenTime);
        // 使用JWT工具加密token信息生成一个包含这些信息的值
        return JWTUtil.createToken(params, NoneJWTSigner.NONE);
    }

    /**
     * 验证token是否有效
     * @param token token值
     * @return 是否有效
     */
    public static boolean verifyToken(String token){
        if (token == null || StrUtil.isEmpty(token)){
            return false;
        }
        // 将token 进行JWT解密
        JSONObject payloads = JWTUtil.parseToken(token).getPayloads();
        long expireTime = payloads.get(EXPIRE_TIME, Long.class);
        String start = payloads.get(CREATE_TIME, String.class);
        long between = DateUtil.between(DateUtil.parse(start), DateUtil.parse(Environment.getSystemTime()), DateUnit.MINUTE);
        return between < expireTime;
    }

    /**
     * 获取token的携带信息
     * @param token token值
     * @return 携带信息
     */
    public static String getTokenValue(String token){
        return getTokenValue(token, TOKEN_NAME, String.class);
    }

    /**
     * 获取token参数信息
     * @param token token值
     * @param name 参数名称
     * @param tClass 参数类型
     * @param <T> 参数泛型
     * @return 参数值
     */
    public static <T> T getTokenValue(String token, String name, Class<T> tClass){
        if (!verifyToken(token)){
            return null;
        }
        return JWTUtil.parseToken(token).getPayloads().get(name, tClass);
    }
}
