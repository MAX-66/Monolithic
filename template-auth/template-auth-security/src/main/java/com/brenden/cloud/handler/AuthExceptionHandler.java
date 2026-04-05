package com.brenden.cloud.handler;

import com.brenden.cloud.base.ResultEntity;
import com.brenden.cloud.constant.GlobalCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-04 12:11
 */

@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {

        /**
     * Spring Security 认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResultEntity<?> handleAuthException(AuthenticationException e) {
        if (e instanceof BadCredentialsException) {
            return ResultEntity.fail(GlobalCodeEnum.GC_800002.getCode(), "用户名或密码错误");
        } else if (e instanceof UsernameNotFoundException) {
            return ResultEntity.fail(GlobalCodeEnum.GC_800002.getCode(), "用户名不存在");
        } else if (e instanceof LockedException) {
            return ResultEntity.fail(GlobalCodeEnum.GC_800002.getCode(), "账号被锁定");
        } else if (e instanceof DisabledException) {
            return ResultEntity.fail(GlobalCodeEnum.GC_800002.getCode(), "账号已禁用");
        }
        return ResultEntity.fail(GlobalCodeEnum.GC_800002);
    }

}
