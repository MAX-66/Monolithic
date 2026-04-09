package com.brenden.cloud.utils;

import com.brenden.cloud.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-04-02 11:02
 */
public final class SecurityUtil {

    public static Long getUserId() {
        return getUserDO().map(UserEntity::getId).orElse(0L);
    }

    public static Optional<UserEntity> getUserDO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtils.isEmpty(authentication)) {
            return Optional.empty();
        }
        return Optional.of((UserEntity) authentication.getPrincipal());
    }

}
