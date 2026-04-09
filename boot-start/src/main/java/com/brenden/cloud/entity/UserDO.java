package com.brenden.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brenden.cloud.base.BaseDO;
import lombok.Data;

import java.io.Serial;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-07 15:34
 */
@Data
@TableName("sys_user")
public class UserDO extends BaseDO {
    @Serial
    private static final long serialVersionUID = 359398655904933696L;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    @TableField("status")
    private Byte status;
}
