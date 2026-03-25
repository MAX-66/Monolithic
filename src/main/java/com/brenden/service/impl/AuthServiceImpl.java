package com.brenden.service.impl;

import com.brenden.constant.GlobalCodeEnum;
import com.brenden.domain.UserDO;
import com.brenden.dto.LoginUser;
import com.brenden.exception.BusinessException;
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
            throw new BusinessException(GlobalCodeEnum.GC_800000.getCode(), "请输入用户名！");
        }

        if (!StringUtils.hasText(req.getPassword())) {
            throw new BusinessException(GlobalCodeEnum.GC_800000.getCode(), "请输入密码！");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        Instant now = Instant.now();
        // 登录成功用户
        UserDO user = (UserDO) authentication.getPrincipal();

        // 生成 token（推荐 UUID）
        String token = UUID.randomUUID().toString();

        // 缓存登录信息
        LoginUser loginUser = buildLoginUser(user, now);
        cacheLoginUser(token, loginUser);

        return buildLoginResp(loginUser, token);
    }

    private LoginUser buildLoginUser(UserDO user, Instant issuedAt) {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setRoles(user.getRoles());
        loginUser.setEnabled(user.isEnabled());
        loginUser.setAccountNonExpired(user.isAccountNonExpired());
        loginUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
        loginUser.setAccountNonLocked(user.isAccountNonLocked());
        loginUser.setIssuedAt(issuedAt);
        loginUser.setExpiresIn(TOKEN_EXPIRES);
        loginUser.setExpiresAt(issuedAt.plusSeconds(TOKEN_EXPIRES));
        return loginUser;
    }


    private LoginResp buildLoginResp(LoginUser user, String token) {
        LoginResp loginResp = new LoginResp();
        loginResp.setTokenValue(token);
        loginResp.setIssuedAt(user.getIssuedAt().toEpochMilli());
        loginResp.setExpiresIn(TOKEN_EXPIRES);
        loginResp.setExpiresAt(user.getExpiresAt().toEpochMilli());
        return loginResp;
    }

    /**
     * 缓存登录用户信息
     */
    private void cacheLoginUser(String token, LoginUser user) {
        redisService.set(LOGIN_TOKEN_KEY + token, user, TOKEN_EXPIRES);
    }
}
