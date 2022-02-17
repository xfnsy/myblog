package com.banxian.myblog.service;

import com.banxian.myblog.domain.SysAttachment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 附件表 服务类
 * </p>
 *
 * @author wangpeng
 * @since 2022-01-25
 */
public interface ISysAttachmentService extends IService<SysAttachment> {

    SysAttachment saveFile(MultipartFile uploadFile) throws IOException;

}
