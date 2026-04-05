package com.brenden.cloud.vo;

import com.brenden.cloud.base.PageEntity;
import lombok.Data;

import java.io.Serial;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-02 17:27
 */
@Data
public class DictPageReq extends PageEntity {

    @Serial
    private static final long serialVersionUID = 6825282879688491024L;

    private String dictType;

    private String dictCode;

}
