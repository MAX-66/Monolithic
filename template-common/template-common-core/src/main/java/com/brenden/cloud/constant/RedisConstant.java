package com.brenden.cloud.constant;

/**
 * Redis 常量定义
 *
 * @author lxq
 * @date 2026-03-22 07:50
 */
public final class RedisConstant {

    public static final String LOGIN_TOKEN_KEY = "auth:token:";

    public static final String REFRESH_TOKEN_KEY = "auth:refresh:";

    public static final int TOKEN_EXPIRES = 1800;

    public static final int REFRESH_TOKEN_EXPIRES = 604800;
}
