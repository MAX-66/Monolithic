package com.brenden.vo.resp;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典分页
 *
 * @author lxq
 * @date 2026-04-02 17:37
 */
@Data
public class DictPageResp implements Serializable {
    @Serial
    private static final long serialVersionUID = 6143845447060669527L;

    private String dictType;

    private String dictCode;

    private String dictLabel;

    private Integer status;

    private Integer sort;
}
