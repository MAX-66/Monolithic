package com.brenden.cloud.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brenden.cloud.entity.DictDO;
import com.brenden.cloud.mapper.DictMapper;
import com.brenden.cloud.service.DictService;
import com.brenden.cloud.vo.req.DictPageReq;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 系统字典表 服务实现类
 * </p>
 *
 * @author lxq
 * @since 2026-04-02
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictDO> implements DictService {

    @Override
    public IPage<DictDO> page(DictPageReq req) {
        IPage<DictDO> page = new Page<>(req.getPageNum(), req.getPageSize());
        return this.lambdaQuery()
                .in(StringUtils.hasText(req.getDictCode()), DictDO::getDictType, req.getDictCode())
                .in(StringUtils.hasText(req.getDictCode()), DictDO::getDictCode, req.getDictCode())
                .page(page);
    }

}
