package com.banxian.myblog.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author wangpeng
 * @since 2022-01-25
 */
public class SysAttachment implements Serializable {

    private static final long serialVersionUID = 4984433916074412229L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 关联业务id
     */
    private Integer businessId;

    /**
     * 关联业务类型
     */
    private String businessType;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型,mimetype
     */
    private String fileType;

    /**
     * 文件大小,字节
     */
    private Long fileSize;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件路径hash
     */
    private String fileHash;

    /**
     * 图片高
     */
    private Integer width;

    /**
     * 图片宽
     */
    private Integer height;

    /**
     * 文件字节
     */
    private Blob files;

    /**
     * 文件字节转为base64
     */
    private String file64;

    /**
     * 是否删除 1：是  0：否
     */
    @TableLogic
    private String deleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
    public Blob getFiles() {
        return files;
    }

    public void setFiles(Blob files) {
        this.files = files;
    }
    public String getFile64() {
        return file64;
    }

    public void setFile64(String file64) {
        this.file64 = file64;
    }
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
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
        return "SysAttachment{" +
            "id=" + id +
            ", businessId=" + businessId +
            ", businessType=" + businessType +
            ", fileName=" + fileName +
            ", fileType=" + fileType +
            ", fileSize=" + fileSize +
            ", filePath=" + filePath +
            ", fileHash=" + fileHash +
            ", width=" + width +
            ", height=" + height +
            ", files=" + files +
            ", file64=" + file64 +
            ", deleted=" + deleted +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
        "}";
    }
}
