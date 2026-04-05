package com.brenden.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brenden.cloud.entity.DictDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统字典表 Mapper 接口
 * </p>
 *
 * @author lxq
 * @since 2026-04-02
 */
@Mapper
public interface DictMapper extends BaseMapper<DictDO> {

}
