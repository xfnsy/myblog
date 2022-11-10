package com.banxian.myblog.service.impl;

import com.banxian.myblog.common.base.UserInfo;
import com.banxian.myblog.common.util.JwtUtil;
import com.banxian.myblog.common.util.PasswordUtil;
import com.banxian.myblog.domain.Admin;
import com.banxian.myblog.domain.result.JwtToken;
import com.banxian.myblog.domain.saveparam.LoginParam;
import com.banxian.myblog.exception.RefreshTokenInvalidException;
import com.banxian.myblog.service.IAdminService;
import com.banxian.myblog.service.ILoginService;
import com.banxian.myblog.support.helper.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService implements ILoginService {

    @Resource
    private IAdminService adminService;

    @Override
    public JwtToken loginJwt(LoginParam loginParam) {
        // 判断登陆密码
        Admin admin = validAccountPwd(loginParam);
        return createJwtToken(admin);
    }

    @Override
    public JwtToken refreshToken(JwtToken jwtToken) {
        try {
            UserInfo userInfo = JwtUtil.parseToken(jwtToken.getRefreshToken(), UserInfo.class);
            Admin admin = adminService.getById(userInfo.getUserId());
            return createJwtToken(admin);
        }catch (Exception e){
            throw new RefreshTokenInvalidException("RefreshToken已失效或无效，请重新登录");
        }
    }

    private Admin validAccountPwd(LoginParam loginParam) {
        // 密码校验
        Admin admin = new Admin();
        admin.setPassword(PasswordUtil.encrypt(loginParam.getPassword()));
        admin.setAdminName(loginParam.getAdminName());
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(admin);
        admin = adminService.getOne(queryWrapper);
        Assert.notNull(admin, "密码或账户名错误");
        return admin;
    }


    private JwtToken createJwtToken(Admin admin) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(admin.getId());
        // refreshToken三天
        String refreshToken = JwtUtil.createRefreshJWT(userInfo);
        // token默认时间30分钟
        userInfo.setUserName(admin.getAdminName());
        userInfo.setAvatar(admin.getAvatar());
        userInfo.setNickname(admin.getNickname());
        String token = JwtUtil.createJWT(userInfo);

        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setRefreshToken(refreshToken);
        return jwtToken;
    }


}
