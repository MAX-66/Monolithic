package com.brenden.constant;

import lombok.Getter;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-24 02:38
 */

@Getter
public enum GlobalCodeEnum {

    GC_0("0", "操作成功!"),

    GC_500("500", "操作失败!");


    private final String code;

    private final String msg;


    GlobalCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static GlobalCodeEnum getEnum(String code) {
        for (GlobalCodeEnum ele : GlobalCodeEnum.values()) {
            if (ele.code.equals(code)) {
                return ele;
            }
        }
        return null;
    }

}
