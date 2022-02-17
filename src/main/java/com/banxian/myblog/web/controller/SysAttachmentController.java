package com.banxian.myblog.web.controller;


import com.banxian.myblog.domain.SysAttachment;
import com.banxian.myblog.service.ISysAttachmentService;
import com.banxian.myblog.web.view.JsonView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 附件表 前端控制器
 * </p>
 *
 * @author wangpeng
 * @since 2022-01-25
 */
@RestController
@RequestMapping("/sysAttachment")
public class SysAttachmentController {

    @Resource
    private ISysAttachmentService sysAttachmentService;


    @PostMapping("/upload")
    public JsonView upload(@RequestParam("file") MultipartFile file) throws Exception {
        SysAttachment sysAttachment = sysAttachmentService.saveFile(file);
        return JsonView.suc("fileUrl", sysAttachment.getFilePath());
    }

}
