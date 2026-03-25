package com.brenden.constant;

import lombok.Getter;

/**
 * 80：系统异常，81：业务异常
 *
 * @author lxq
 * @date 2026-03-24 02:38
 */

@Getter
public enum GlobalCodeEnum {

    GC_0("0", "操作成功!"),

    GC_500("500", "操作失败!"),

    GC_800000("800000", "参数错误!"),

    GC_800002("800002", "认证失败!"),

    GC_800004("800004", "登录已过期,请重新登录!"),

    GC_809999("809999", "系统异常！请联系管理员");


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
