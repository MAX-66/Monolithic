package com.brenden.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brenden.cloud.constant.GlobalCodeEnum;
import com.brenden.cloud.dto.UserRoleDTO;
import com.brenden.cloud.entity.UserDO;
import com.brenden.cloud.entity.UserEntity;
import com.brenden.cloud.exception.BusinessException;
import com.brenden.cloud.mapper.UserMapper;
import com.brenden.cloud.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-05 11:25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserDetailsService, UserService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRoleDTO dto = this.baseMapper.getByUsername(username);
        if (Objects.isNull(dto)) {
            throw new BusinessException(GlobalCodeEnum.GC_800002.getCode(), "用户名或密码错误");
        }
        return buildUserEntity(dto);
    }

    private static UserEntity buildUserEntity(UserRoleDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.getUserId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRoles(dto.getRoles());
        return entity;
    }

    @Override
    public UserEntity getUserRoleById(Long userId) {
        UserRoleDTO dto = this.baseMapper.getUserById(userId);
        if (Objects.isNull(dto)) {
            throw new BusinessException(GlobalCodeEnum.GC_800002.getCode(), "用户名或密码错误");
        }
        return buildUserEntity(dto);
    }
}
