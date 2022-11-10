package com.banxian.myblog.service.impl;

import com.banxian.myblog.common.util.SmMsUtil;
import com.banxian.myblog.domain.SmMsFile;
import com.banxian.myblog.domain.SysAttachment;
import com.banxian.myblog.mapper.SysAttachmentMapper;
import com.banxian.myblog.service.ISysAttachmentService;
import com.banxian.myblog.support.helper.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 * 附件表 服务实现类
 * </p>
 *
 * @author wangpeng
 * @since 2022-01-25
 */
@Service
public class SysAttachmentServiceImpl extends ServiceImpl<SysAttachmentMapper, SysAttachment> implements ISysAttachmentService {

    @Override
    public SysAttachment saveFile(MultipartFile uploadFile) throws IOException {
        File file = new File("e:/" + uploadFile.getOriginalFilename());
        uploadFile.transferTo(file);
        Assert.mustBeTrue(file.exists(),"文件不存在");
        SmMsFile smMsFile = SmMsUtil.upload(file);
        Assert.notNull(smMsFile,"上传文件失败");
        SysAttachment sysAttachment=new SysAttachment();
        sysAttachment.setFileType(uploadFile.getContentType());
        sysAttachment.setFileSize(uploadFile.getSize());
        sysAttachment.setFilePath(smMsFile.getUrl());
        sysAttachment.setFileHash(smMsFile.getHash());
        sysAttachment.setFileName(smMsFile.getFilename());
        sysAttachment.setHeight(smMsFile.getHeight());
        sysAttachment.setWidth(smMsFile.getWidth());
        save(sysAttachment);
        return sysAttachment;
    }
}
