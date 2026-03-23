package com.brenden.base;

import com.brenden.constant.GlobalCodeEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-24 02:37
 */

@Data
public class ResultEntity<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -6792063637134743788L;

    private String resultCode;

    private String resultMsg;

    private T data;

    private long timestamp = System.currentTimeMillis();


    public static <T> ResultEntity<T> success(T data) {
        ResultEntity<T> result = new ResultEntity<>();
        result.data = data;
        result.resultCode = GlobalCodeEnum.GC_0.getCode();
        result.resultMsg = GlobalCodeEnum.GC_0.getMsg();
        return result;
    }



    public static <T> ResultEntity<T> fail(String errorCode, String errorMsg) {
        ResultEntity<T> result = new ResultEntity<>();
        result.setResultCode(errorCode);
        result.setResultMsg(errorMsg);
        return result;
    }

    public static <T> ResultEntity<T> fail(GlobalCodeEnum codeEnum) {
        return fail(codeEnum.getCode(), codeEnum.getMsg());
    }
}
