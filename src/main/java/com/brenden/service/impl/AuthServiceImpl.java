package com.brenden.service.impl;

import com.brenden.domain.UserDO;
import com.brenden.service.AuthService;
import com.brenden.service.RedisService;
import com.brenden.vo.req.LoginReq;
import com.brenden.vo.resp.LoginResp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.UUID;

import static com.brenden.constant.RedisConstant.LOGIN_TOKEN_KEY;
import static com.brenden.constant.RedisConstant.TOKEN_EXPIRES;

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

    private final RedisService redisService;

    @Override
    public LoginResp login(LoginReq req){

        if (!StringUtils.hasText(req.getUsername())) {
            return null;
        }

        if (!StringUtils.hasText(req.getPassword())) {
            return null;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        // 登录成功用户
        UserDO user = (UserDO) authentication.getPrincipal();

        // 生成 token（推荐 UUID）
        String token = UUID.randomUUID().toString();

        // 缓存 token
//        redisService.del();
        redisService.set(LOGIN_TOKEN_KEY + token, user, TOKEN_EXPIRES);

        LoginResp loginResp = new LoginResp();
        loginResp.setTokenValue(token);
        Instant now = Instant.now();
        loginResp.setIssuedAt(now);
        loginResp.setExpiresIn(TOKEN_EXPIRES);
        loginResp.setExpiresAt(now.plusSeconds(TOKEN_EXPIRES));

        return loginResp;
    }
}
