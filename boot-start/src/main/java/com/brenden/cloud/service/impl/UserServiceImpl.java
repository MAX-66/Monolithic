package com.brenden.cloud.service.impl;

import com.brenden.cloud.entity.UserDO;
import com.brenden.cloud.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-05 11:25
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = new UserDO();
        userDO.setId(1L);
        userDO.setUsername("admin");
        userDO.setPassword("{noop}123456");
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        userDO.setRoles(roles);
        return userDO;
    }
}
