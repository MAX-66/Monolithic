package com.brenden.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * 响应工具类
 *
 * @author lxq
 * @date 2026-04-02 09:48
 */
public final class ResponseUtil {

    private ResponseUtil() {
    }

    /**
     * 统一返回 JSON
     */
    public static void write(HttpServletResponse response, String str) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(str);
    }

}
