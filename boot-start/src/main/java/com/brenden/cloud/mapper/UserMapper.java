package com.brenden.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brenden.cloud.dto.UserRoleDTO;
import com.brenden.cloud.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author lxq
 * @since 2026-04-07
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    UserRoleDTO getByUsername(String username);

    UserRoleDTO getUserById(Long userId);

}
