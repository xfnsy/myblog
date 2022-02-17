package com.banxian.myblog.web.controller;


import com.banxian.myblog.common.base.UserInfo;
import com.banxian.myblog.common.util.PasswordUtil;
import com.banxian.myblog.common.util.PoiExcelUtil;
import com.banxian.myblog.domain.Admin;
import com.banxian.myblog.domain.User;
import com.banxian.myblog.domain.saveparam.PasswordParam;
import com.banxian.myblog.service.IAdminService;
import com.banxian.myblog.support.helper.TokenHelper;
import com.banxian.myblog.support.helper.UserInfoHelper;
import com.banxian.myblog.web.view.JsonView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wangpeng
 * @since 2020-12-16
 */
@RestController
public class UserController {

    @Resource
    private IAdminService adminService;

    @GetMapping("/user/info")
    public JsonView info() {
        return JsonView.suc("info", UserInfoHelper.get());
    }

    @PutMapping("/user/edit")
    public JsonView editUser(@RequestBody Admin admin) {
        admin.setId(UserInfoHelper.get().getUserId());
        adminService.updateById(admin);
        TokenHelper.invalidToken();
        return JsonView.sucUpdate();
    }

    @PutMapping("/user/editPwd")
    public JsonView editUserPwd(@RequestBody PasswordParam passwordParam) {
        Admin admin = adminService.getById(UserInfoHelper.get().getUserId());
        if(!admin.getPassword().equals(PasswordUtil.encrypt(passwordParam.getOldPwd()))){
            return JsonView.fail("旧的密码错误");
        }
        admin.setPassword(PasswordUtil.encrypt(passwordParam.getNewPwd()));
        adminService.updateById(admin);
        TokenHelper.invalidToken();
        return JsonView.sucUpdate();
    }



    @GetMapping("/user/export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "用户信息";
        String sheetName = "用户信息";
        String[] headerNames = {"姓名", "年龄", "出生日期", "备注"};
        String[] attrNames = {"name", "age", "birthday", "remark"};
        List<User> datas = new ArrayList<>();
        PoiExcelUtil.exportExcel(sheetName, headerNames, attrNames, datas, fileName, request, response);
    }

    @GetMapping("/user/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/octem-stream");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Length", String.valueOf(Paths.get("d:/干部人事档案采集系统5.5.8.exe").toFile().length()));
        response.setHeader("Content-Disposition", "attachment;filename=" + Base64.getEncoder().encodeToString("干部人事档案采集系统5.5.8.exe".getBytes(StandardCharsets.UTF_8)));
        ServletOutputStream outputStream = response.getOutputStream();
        Files.copy(Paths.get("d:/干部人事档案采集系统5.5.8.exe"), outputStream);
    }
}

