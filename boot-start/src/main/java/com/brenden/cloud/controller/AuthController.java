package com.brenden.cloud.controller;

import com.brenden.cloud.base.ResultEntity;
import com.brenden.cloud.service.AuthService;
import com.brenden.cloud.vo.LoginReq;
import com.brenden.cloud.vo.resp.LoginResp;
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

    @PostMapping("/login")
    public ResultEntity<LoginResp> login(@RequestBody LoginReq req) throws Exception {
        return ResultEntity.success(authService.login(req));
    }

}
