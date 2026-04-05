package com.brenden.cloud.exception;

import com.brenden.cloud.base.ResultEntity;
import com.brenden.cloud.constant.GlobalCodeEnum;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-24 03:03
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResultEntity<?> handleBusinessException(BusinessException e) {
        return ResultEntity.fail(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultEntity<?> handleValidException(MethodArgumentNotValidException e) {
        return ResultEntity.fail(GlobalCodeEnum.GC_800000);
    }

    /**
     * 权限异常
     */
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResultEntity<?> handleAccessDenied(AccessDeniedException e) {
//        return ResultEntity.fail(GlobalCodeEnum.GC_800003);
//    }

    /**
     * 其他异常（兜底）
     */
    @ExceptionHandler(Exception.class)
    public ResultEntity<?> handleException(Exception e) {
        log.error("系统异常:", e);
        return ResultEntity.fail(GlobalCodeEnum.GC_809999);
    }

}
