package com.brenden.cloud.vo.resp;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录响应
 *
 * @author lxq
 * @date 2026-03-20 08:25
 */
@Data
public class LoginResp implements Serializable {
    @Serial
    private static final long serialVersionUID = -1256543716660445532L;

    private String tokenValue;

    private Long issuedAt;

    private Long expiresAt;

    private Integer expiresIn;

    private String refreshToken;

    private Long refreshExpiresAt;

    private Integer refreshExpiresIn;

    private String tokenType;

}
