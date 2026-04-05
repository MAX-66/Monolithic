package com.brenden.cloud.utils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-02 16:20
 */
public final class TokenUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private TokenUtil() {
    }

    /**
     * 方式一：UUID
     */
    public static String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 方式二：随机 Token
     */
    public static String generateSecureToken() {
        byte[] bytes = new byte[32]; // 256 bit
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
