package com.brenden.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.brenden.constant.SysConstant;
import com.brenden.util.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomizedMetaObjectHandler implements MetaObjectHandler {

    public static final String GMT_CREATE = "gmtCreate";

    public static final String GMT_MODIFIED = "gmtModified";

    public static final String DEL_FLAG = "isDelete";

    public static final String CREATE_BY = "createBy";

    public static final String UPDATE_BY = "updateBy";


    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, GMT_CREATE, LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, GMT_MODIFIED, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, DEL_FLAG, () -> SysConstant.DEFAULT, Integer.class);
        Long userId = SecurityUtil.getUserId();
        this.strictInsertFill(metaObject, CREATE_BY, () -> userId, Long.class);
        this.strictInsertFill(metaObject, UPDATE_BY, () -> userId, Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, GMT_MODIFIED, LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, UPDATE_BY, SecurityUtil::getUserId, Long.class);
    }
}
