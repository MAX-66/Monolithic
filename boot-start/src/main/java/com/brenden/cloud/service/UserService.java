package com.brenden.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brenden.cloud.entity.UserDO;
import com.brenden.cloud.entity.UserEntity;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-07 15:07
 */
public interface UserService extends IService<UserDO> {

    UserEntity getUserRoleById(Long userId);

}
