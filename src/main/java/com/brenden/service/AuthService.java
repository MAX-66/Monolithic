package com.brenden.service;

import com.brenden.vo.req.LoginReq;
import com.brenden.vo.resp.LoginResp;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-20 09:56
 */
public interface AuthService {

    LoginResp login(LoginReq req);

}
