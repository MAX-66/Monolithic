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
        String tokenValue = ServletUtil.getAuthorizationValue(request);

        Object userObj = redisService.get(RedisConstant.LOGIN_TOKEN_KEY + tokenValue);
        if (Objects.isNull(userObj)) {
            ServletUtil.write(response, JacksonUtil.toJson(ResultEntity.fail(GlobalCodeEnum.GC_800004)));
            return;
        }
        UserDO userDO = buildUserDO((LoginUser) userObj);
        if (userDO.getExpiresAt() <= System.currentTimeMillis()) {
            ServletUtil.write(response, JacksonUtil.toJson(ResultEntity.fail(GlobalCodeEnum.GC_800004)));
            return;
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
        return "/auth/login".equals(uri);
    }

}
