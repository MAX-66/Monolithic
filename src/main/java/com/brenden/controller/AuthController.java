package com.brenden.controller;

import com.brenden.service.AuthService;
import com.brenden.vo.req.LoginReq;
import com.brenden.vo.resp.LoginResp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
    public LoginResp login(@RequestBody LoginReq req) {
        return authService.login(req);
    }

}
