package com.brenden.cloud.vo.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-20 05:26
 */

@Data
public class LoginReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 5310242478416690235L;

    private String username;

    private String password;
}
