package com.banxian.myblog.web.controller;


import com.banxian.myblog.common.anno.ExcludedPath;
import com.banxian.myblog.common.base.Constants;
import com.banxian.myblog.common.base.UserInfo;
import com.banxian.myblog.common.util.JwtUtil;
import com.banxian.myblog.common.util.PasswordUtil;
import com.banxian.myblog.common.util.SHAUtil;
import com.banxian.myblog.domain.Admin;
import com.banxian.myblog.domain.TokenRecord;
import com.banxian.myblog.service.IAdminService;
import com.banxian.myblog.service.ITokenRecordService;
import com.banxian.myblog.support.helper.TokenHelper;
import com.banxian.myblog.support.helper.UserInfoHelper;
import com.banxian.myblog.web.view.JsonView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.tomcat.util.log.UserDataHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private IAdminService adminService;

    @Resource
    private ITokenRecordService tokenRecordService;


    @ExcludedPath
    @PostMapping("/login")
    public JsonView login(@RequestBody Admin admin) {
        // 后端加密
        admin.setPassword(PasswordUtil.encrypt(admin.getPassword()));
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(admin);
        admin = adminService.getOne(queryWrapper);
        if (admin == null) {
            return JsonView.fail("用户名或密码错误");
        }
        // cookie保持登录
//        Cookie accountCookie = new Cookie("account", admin.getAdminName());
//        accountCookie.setMaxAge(60);
//        response.addCookie(accountCookie);
//        Cookie authCookie = new Cookie("auth", MD5Util.MD5(admin.getAdminName(), Constants.MD5_SALT));
//        authCookie.setMaxAge(60);
//        response.addCookie(authCookie);

        // session登陆
//        HttpSession session = request.getSession(true);
//        session.setAttribute("id_" + admin.getAdminId(), admin);
//        session.setMaxInactiveInterval(60);

        // JWT登陆
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(admin.getId());
        userInfo.setUserName(admin.getAdminName());
        userInfo.setAvatar(admin.getAvatar());
        userInfo.setNickname(admin.getNickname());
        String token = JwtUtil.createJWT(userInfo);
        TokenRecord tokenRecord = new TokenRecord();
        tokenRecord.setToken(token);
        tokenRecord.setUserId(admin.getId());
        tokenRecordService.save(tokenRecord);
        return JsonView.suc("token", token);
    }

    /**
     * 通过refreshToken刷新token
     */
    @ExcludedPath
    @GetMapping("/refreshToken")
    public JsonView refreshToken() {
        return JsonView.suc("刷新成功");
    }

    /**
     * 作废jwt token
     * 1.把作废token放到缓存中，但是项目重启token又会生效
     * 2.把作废token放到数据库，比较安全合理
     */
    @GetMapping("/logout")
    public JsonView logout() {
        TokenHelper.invalidToken();
        return JsonView.suc("注销成功");
    }

}
