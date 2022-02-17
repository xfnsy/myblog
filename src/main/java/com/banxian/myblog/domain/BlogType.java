package com.banxian.myblog.domain;
import com.baomidou.mybatisplus.annotation.*;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "BlogType{" +
            "id=" + id +
            ", typeName=" + typeName +
            ", detail=" + detail +
            ", deleted=" + deleted +
            ", valid=" + valid +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
        "}";
    }
}
