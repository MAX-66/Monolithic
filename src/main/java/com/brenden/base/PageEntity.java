package com.brenden.base;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-02 17:12
 */

@Data
public class PageEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3623104989998157274L;

    private Long pageSize;

    private Long pageNum;

    private String orderBy;

    private Boolean sort = true;

}
