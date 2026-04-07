package com.brenden.cloud.service;

import com.brenden.cloud.vo.req.LoginReq;
import com.brenden.cloud.vo.resp.LoginResp;

/**
 * 认证服务接口
 *
 * @author lxq
 * @date 2026-03-20 09:56
 */
public interface AuthService {

    /**
     * 用户登录
     * @param req 登录请求
     * @return 登录响应
     */
    LoginResp login(LoginReq req);

    /**
     * 刷新 Token
     * @param refreshToken 刷新令牌
     * @return 新的登录响应
     */
    LoginResp refreshToken(String refreshToken);

}
