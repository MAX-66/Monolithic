package com.brenden.service.impl;

import com.brenden.service.AuthService;
import com.brenden.vo.req.LoginReq;
import com.brenden.vo.resp.LoginResp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-20 09:57
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    public static final int TOKEN_EXPIRES = 1800;

    @Override
    public LoginResp login(LoginReq req) {

        if (!StringUtils.hasText(req.getUsername())) {
            return null;
        }

        if (!StringUtils.hasText(req.getPassword())) {
            return null;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        // 组装 token
        // 登录成功用户
        UserDetails user = (UserDetails) authentication.getPrincipal();

        // 生成 token（推荐 UUID）
        String token = UUID.randomUUID().toString();

        LoginResp loginResp = new LoginResp();
        loginResp.setTokenValue(token);
        Instant now = Instant.now();
        loginResp.setIssuedAt(now);
        loginResp.setExpiresIn(TOKEN_EXPIRES);
        loginResp.setExpiresAt(now.plusSeconds(TOKEN_EXPIRES));

        return loginResp;
    }
}
