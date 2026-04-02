package com.brenden.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brenden.base.ResultEntity;
import com.brenden.entity.DictDO;
import com.brenden.service.DictService;
import com.brenden.vo.req.DictPageReq;
import com.brenden.vo.resp.DictPageResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-02 17:08
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/dict")
public class DictController {

    private final DictService dictService;

    @RequestMapping("/page")
    public ResultEntity<IPage<DictPageResp>> page(DictPageReq req) {
        IPage<DictDO> page = dictService.page(req);
        IPage<DictPageResp> convert = page.convert(dictDO -> {
            DictPageResp dictPageResp = new DictPageResp();
            dictPageResp.setDictType(dictDO.getDictType());
            dictPageResp.setDictLabel(dictDO.getDictLabel());
            dictPageResp.setDictCode(dictDO.getDictCode());
            dictPageResp.setStatus(dictDO.getStatus());
            dictPageResp.setSort(dictDO.getSort());
            return dictPageResp;
        });
        return ResultEntity.success(convert);
    }

}
