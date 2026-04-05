package com.brenden.cloud.exception;

import com.brenden.cloud.constant.GlobalCodeEnum;
import lombok.Getter;

import java.io.Serial;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-24 03:01
 */

@Getter
public class BusinessException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -2075845659448076691L;


    private final String errorCode;

    private final String errorMsg;

    public BusinessException(GlobalCodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.errorCode = codeEnum.getCode();
        this.errorMsg = codeEnum.getMsg();
    }

    public BusinessException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
