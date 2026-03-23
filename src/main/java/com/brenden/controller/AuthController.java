package com.brenden.controller;

import com.brenden.base.ResultEntity;
import com.brenden.service.AuthService;
import com.brenden.vo.req.LoginReq;
import com.brenden.vo.resp.LoginResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证授权接口
 *
 * @author lxq
 * @date 2026-03-20 05:24
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResultEntity<LoginResp> login(@RequestBody LoginReq req) throws Exception {
        return ResultEntity.success(authService.login(req));
    }

}
