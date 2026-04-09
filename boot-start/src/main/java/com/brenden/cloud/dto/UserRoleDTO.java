package com.brenden.cloud.dto;

import lombok.Data;

import java.util.List;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-07 17:15
 */
@Data
public class UserRoleDTO {

    private Long userId;

    private String username;

    private String password;

    private String mobile;

    private String email;

    private String status;

    private List<String> roles;

}
