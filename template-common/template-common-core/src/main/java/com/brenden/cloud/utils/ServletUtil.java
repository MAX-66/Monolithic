package com.brenden.cloud.utils;

import com.brenden.cloud.constant.SysConstant;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-03 16:54
 */
@Slf4j
public final class ServletUtil {

    /**
     * 统一返回 JSON
     */
    public static void write(HttpServletResponse response, String str) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(str);
    }

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes not be null");
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }


    public static String getAuthorizationValue(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            log.debug("Token not found in headers. Trying request parameters.");
            token = request.getParameter(AUTHORIZATION);
            if (StringUtils.isBlank(token)) {
                log.debug("Token not found in request parameters.  Not an OAuth2 request.");
                return token;
            }
        }
        return token.substring(SysConstant.BEARER_TOKEN.length()).trim();
    }


}
