package com.brenden.filter;

import com.brenden.constant.GlobalCodeEnum;
import com.brenden.constant.RedisConstant;
import com.brenden.domain.UserDO;
import com.brenden.exception.BusinessException;
import com.brenden.service.RedisService;
import com.brenden.util.RequestUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * 填充 Authentication
 *
 * @author lxq
 * @date 2026-03-19 09:23
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = RequestUtil.getAuthorizationValue(request);
        UserDO userDO = getUserDO(tokenValue);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDO, null, userDO.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private UserDO getUserDO(String token) {
        Object userObj = redisService.get(RedisConstant.LOGIN_TOKEN_KEY + token);
        if (Objects.isNull(userObj)) {
            throw new BusinessException(GlobalCodeEnum.GC_800004);
        }


        return null;
    }


}
