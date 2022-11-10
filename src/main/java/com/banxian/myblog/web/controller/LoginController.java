package com.banxian.myblog.web.controller;


import com.banxian.myblog.common.anno.ExcludedPath;
import com.banxian.myblog.domain.result.JwtToken;
import com.banxian.myblog.domain.saveparam.LoginParam;
import com.banxian.myblog.service.ILoginService;
import com.banxian.myblog.web.view.JsonView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private ILoginService loginService;


    @ExcludedPath
    @PostMapping("/login")
    public JsonView login(@RequestBody LoginParam loginParam) {
        return JsonView.suc("tokens", loginService.loginJwt(loginParam));
    }

    /**
     * 通过refreshToken刷新token
     */
    @ExcludedPath
    @PostMapping("/refreshToken")
    public JsonView refreshToken(@RequestBody JwtToken jwtToken) {
        return JsonView.suc("tokens", loginService.refreshToken(jwtToken));
    }

    /**
     * 作废jwt token
     * 1.把作废token放到缓存中，但是项目重启token又会生效
     * 2.把作废token放到数据库，比较安全合理
     */
    @GetMapping("/logout")
    public JsonView logout() {
        return JsonView.suc("注销成功");
    }

}
