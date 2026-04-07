package com.brenden.cloud.service.impl;

import com.brenden.cloud.constant.GlobalCodeEnum;
import com.brenden.cloud.constant.SysConstant;
import com.brenden.cloud.entity.LoginUser;
import com.brenden.cloud.entity.UserDO;
import com.brenden.cloud.exception.BusinessException;
import com.brenden.cloud.service.AuthService;
import com.brenden.cloud.service.RedisService;
import com.brenden.cloud.utils.TokenUtil;
import com.brenden.cloud.vo.req.LoginReq;
import com.brenden.cloud.vo.resp.LoginResp;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Objects;

import static com.brenden.cloud.constant.RedisConstant.*;


/**
 * 认证服务实现
 *
 * @author lxq
 * @date 2026-03-20 09:57
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final RedisService redisService;

    @Override
    @SneakyThrows
    public LoginResp login(LoginReq req){
        if (!StringUtils.hasText(req.getUsername())) {
            throw new BusinessException(GlobalCodeEnum.GC_800000.getCode(), "请输入用户名！");
        }

        if (!StringUtils.hasText(req.getPassword())) {
            throw new BusinessException(GlobalCodeEnum.GC_800000.getCode(), "请输入密码！");
        }
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        Instant now = Instant.now();
        // 登录成功用户
        UserDO user = (UserDO) authentication.getPrincipal();

        // 生成 Access Token 和 Refresh Token
        String accessToken = TokenUtil.generateSecureToken();
        String refreshToken = TokenUtil.generateSecureToken();

        // 计算过期时间
        Instant refreshExpiresAt = now.plusSeconds(REFRESH_TOKEN_EXPIRES);

        // 缓存登录信息
        LoginUser loginUser = buildLoginUser(user, now);
        cacheAccessToken(accessToken, loginUser);
        cacheRefreshToken(refreshToken, user.getId());

        return buildLoginResp(loginUser, accessToken, refreshToken, now);
    }

    @Override
    public LoginResp refreshToken(String refreshToken) {
        if (!StringUtils.hasText(refreshToken)) {
            throw new BusinessException(GlobalCodeEnum.GC_800000.getCode(), "刷新令牌不能为空！");
        }

        // 从 Redis 获取用户 ID
        Object userIdObj = redisService.get(REFRESH_TOKEN_KEY + refreshToken);
        if (userIdObj == null) {
            throw new BusinessException(GlobalCodeEnum.GC_800004.getCode(), "刷新令牌已过期，请重新登录！");
        }

        Long userId = (Long) userIdObj;

        // 这里应该从数据库查询完整的用户信息
        // 暂时使用硬编码的方式，实际项目中需要注入 UserService
        UserDO user = getUserById(userId);
        if (Objects.isNull(user)) {
            throw new BusinessException(GlobalCodeEnum.GC_800002.getCode(), "用户不存在！");
        }

        Instant now = Instant.now();


        // 生成新的 Token
        String newAccessToken = TokenUtil.generateSecureToken();
        String newRefreshToken = TokenUtil.generateSecureToken();

        // 缓存新的 Token
        LoginUser loginUser = buildLoginUser(user, now);
        cacheAccessToken(newAccessToken, loginUser);
        cacheRefreshToken(newRefreshToken, userId);

        // 删除旧的 Refresh Token
        redisService.del(REFRESH_TOKEN_KEY + refreshToken);

        return buildLoginResp(loginUser, newAccessToken, newRefreshToken, now);
    }

    /**
     * 根据用户 ID 获取用户信息
     * TODO: 实际项目中应该从数据库查询
     */
    private UserDO getUserById(Long userId) {
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        userDO.setUsername("admin");
        userDO.setPassword("{noop}123456");
        userDO.setEnabled(true);
        userDO.setAccountNonExpired(true);
        userDO.setAccountNonLocked(true);
        userDO.setCredentialsNonExpired(true);
        java.util.List<String> roles = new java.util.ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        userDO.setRoles(roles);
        return userDO;
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
        loginUser.setIssuedAt(issuedAt.toEpochMilli());
        loginUser.setExpiresIn(TOKEN_EXPIRES);
        loginUser.setExpiresAt(issuedAt.plusSeconds(TOKEN_EXPIRES).toEpochMilli());
        return loginUser;
    }

    private LoginResp buildLoginResp(LoginUser user, String accessToken, String refreshToken, 
                                     Instant issuedAt) {
        LoginResp loginResp = new LoginResp();
        loginResp.setTokenValue(accessToken);
        loginResp.setRefreshToken(refreshToken);
        loginResp.setIssuedAt(issuedAt.toEpochMilli());
        loginResp.setExpiresIn(TOKEN_EXPIRES);
        loginResp.setExpiresAt(user.getExpiresAt());
        loginResp.setTokenType(SysConstant.BEARER_TOKEN);
        
        // 设置 Refresh Token 过期信息
        Instant refreshExpiresAt = issuedAt.plusSeconds(REFRESH_TOKEN_EXPIRES);
        long refreshExpiresAtMillis = refreshExpiresAt.toEpochMilli();
        loginResp.setRefreshExpiresAt(refreshExpiresAtMillis);
        loginResp.setRefreshExpiresIn(REFRESH_TOKEN_EXPIRES);
        
        return loginResp;
    }

    /**
     * 缓存 Access Token
     */
    private void cacheAccessToken(String token, LoginUser user) {
        redisService.set(LOGIN_TOKEN_KEY + token, user, TOKEN_EXPIRES);
    }

    /**
     * 缓存 Refresh Token
     */
    private void cacheRefreshToken(String refreshToken, Long userId) {
        redisService.set(REFRESH_TOKEN_KEY + refreshToken, userId, REFRESH_TOKEN_EXPIRES);
    }

}
