package com.brenden.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brenden.cloud.base.BaseDO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 系统字典表
 * </p>
 *
 * @author lxq
 * @since 2026-04-02
 */
@Getter
@Setter
@TableName("sys_dict")
public class DictDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典类型（如 order_status）
     */
    @TableField("dict_type")
    private String dictType;

    /**
     * 字典值（存数据库的值）
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典显示名称
     */
    @TableField("dict_label")
    private String dictLabel;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 1启用 0停用
     */
    @TableField("status")
    private Integer status;

    /**
     * 备注说明
     */
    @TableField("remark")
    private String remark;
}
