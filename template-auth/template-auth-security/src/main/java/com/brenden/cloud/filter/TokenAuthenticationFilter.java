package com.brenden.cloud.filter;


import com.brenden.cloud.base.ResultEntity;
import com.brenden.cloud.constant.GlobalCodeEnum;
import com.brenden.cloud.constant.RedisConstant;
import com.brenden.cloud.entity.LoginUser;
import com.brenden.cloud.entity.UserDO;
import com.brenden.cloud.service.RedisService;
import com.brenden.cloud.utils.JacksonUtil;
import com.brenden.cloud.utils.ServletUtil;
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
 * Token 认证过滤器
 *
 * @author lxq
 * @date 2026-03-19 09:23
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final RedisService redisService;

    private static final long TOKEN_REFRESH_THRESHOLD = 300;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = ServletUtil.getAuthorizationValue(request);

        Object userObj = redisService.get(RedisConstant.LOGIN_TOKEN_KEY + tokenValue);
        if (Objects.isNull(userObj)) {
            ServletUtil.write(response, JacksonUtil.toJson(ResultEntity.fail(GlobalCodeEnum.GC_800004)));
            return;
        }
        
        LoginUser loginUser = (LoginUser) userObj;
        UserDO userDO = buildUserDO(loginUser);
        
        if (userDO.getExpiresAt() <= System.currentTimeMillis()) {
            ServletUtil.write(response, JacksonUtil.toJson(ResultEntity.fail(GlobalCodeEnum.GC_800004)));
            return;
        }
        
        long remainingTime = (userDO.getExpiresAt() - System.currentTimeMillis()) / 1000;
        if (remainingTime < TOKEN_REFRESH_THRESHOLD) {
            response.setHeader("X-Token-Expiring-Soon", "true");
            response.setHeader("X-Token-Remaining-Seconds", String.valueOf(remainingTime));
        }
        
        Authentication auth = new UsernamePasswordAuthenticationToken(userDO, null, userDO.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }


    private UserDO buildUserDO(LoginUser user) {
        UserDO userDO = new UserDO();
        userDO.setId(user.getId());
        userDO.setUsername(user.getUsername());
        userDO.setRoles(user.getRoles());
        userDO.setEnabled(user.getEnabled());
        userDO.setAccountNonExpired(user.getAccountNonExpired());
        userDO.setCredentialsNonExpired(user.getCredentialsNonExpired());
        userDO.setAccountNonLocked(user.getAccountNonLocked());
        userDO.setIssuedAt(user.getIssuedAt());
        userDO.setExpiresAt(user.getExpiresAt());
        return userDO;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return "/auth/login".equals(uri) || "/auth/refresh".equals(uri);
    }

}
