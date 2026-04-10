package com.brenden.cloud.controller;

import com.brenden.cloud.base.ResultEntity;
import com.brenden.cloud.service.AuthService;
import com.brenden.cloud.utils.ServletUtil;
import com.brenden.cloud.vo.req.LoginReq;
import com.brenden.cloud.vo.req.RefreshTokenReq;
import com.brenden.cloud.vo.resp.LoginResp;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证授权接口
 *
 * @author lxq
 * @date 2026-03-20 05:24
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResultEntity<LoginResp> login(@RequestBody LoginReq req) throws Exception {
        return ResultEntity.success(authService.login(req));
    }

    /**
     * 刷新 Token
     */
    @PostMapping("/refresh")
    public ResultEntity<LoginResp> refresh(@RequestBody RefreshTokenReq req) {
        return ResultEntity.success(authService.refreshToken(req.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResultEntity<Boolean> logout(HttpServletRequest request) {
        String token = ServletUtil.getAuthorizationValue(request);
        return ResultEntity.success(authService.logout(token));
    }

}
