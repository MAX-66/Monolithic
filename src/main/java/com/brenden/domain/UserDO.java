package com.brenden.domain;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-20 02:40
 */
@Setter
public class UserDO implements UserDetails {
    @Serial
    private static final long serialVersionUID = 6375732715323418515L;

    private String id;

    private String password;

    private String username;

    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        return AuthorityUtils.createAuthorityList(roles);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
