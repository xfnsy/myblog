package com.banxian.myblog.domain;
import com.banxian.myblog.mybatis.typehandler.SecretFieldTypeHandler;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

import java.io.Serializable;

/**
 * <p>
 * 博客类型
 * </p>
 *
 * @author wangpeng
 * @since 2022-01-14
 */
@Data
@TableName(autoResultMap = true)
public class BlogType implements Serializable {

    private static final long serialVersionUID = 4344848828462926573L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 详情说明
     */
    private String detail;

    /**
     * 是否删除 1：是  0：否
     */
    @TableLogic
    private String deleted;

    /**
     * 是否有效 1：是  0：否
     */
    private String valid;

    /**
     * 创建时间
     */
    @TableField(value = "create_at", fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    @TableField(value = "update_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateAt;

    @TableField(value = "sfz", typeHandler = SecretFieldTypeHandler.class)
    private String sfz;
}
