package com.brenden.cloud.service;

import com.brenden.cloud.vo.LoginReq;
import com.brenden.cloud.vo.resp.LoginResp;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-20 09:56
 */
public interface AuthService {

    LoginResp login(LoginReq req);

}
