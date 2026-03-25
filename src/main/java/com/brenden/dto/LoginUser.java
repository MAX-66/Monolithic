package com.brenden.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-25 07:13
 */
@Data
public class LoginUser implements Serializable {
    @Serial
    private static final long serialVersionUID = -4832542448023704255L;

    private Long id;

    private String username;

    private List<String> roles;

    private Boolean enabled;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Instant issuedAt;

    private Integer expiresIn;

    private Instant expiresAt;
}
