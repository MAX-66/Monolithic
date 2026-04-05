package com.brenden.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.brenden.cloud.entity.DictDO;
import com.brenden.cloud.vo.DictPageReq;


/**
 * <p>
 * 系统字典表 服务类
 * </p>
 *
 * @author lxq
 * @since 2026-04-02
 */
public interface DictService extends IService<DictDO> {

    /**
     * 字典分页
     * @param req
     * @return
     */
    IPage<DictDO> page(DictPageReq req);


}
