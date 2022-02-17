package com.banxian.myblog.web.controller;


import com.banxian.myblog.service.IAdminService;
import com.banxian.myblog.web.view.JsonView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author wangpeng
 * @since 2020-12-16
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;

    @GetMapping("/list")
    public JsonView list(){
        return JsonView.suc("list",adminService.list());
    }


}

