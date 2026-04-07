package com.brenden.cloud.vo.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 刷新 Token 请求
 *
 * @author lxq
 * @date 2026-04-05 12:00
 */
@Data
public class RefreshTokenReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String refreshToken;
}
