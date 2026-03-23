package com.brenden.vo.resp;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * 做点什么
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
}
