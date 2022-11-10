package com.banxian.myblog.service;

import com.banxian.myblog.domain.result.JwtToken;
import com.banxian.myblog.domain.saveparam.LoginParam;

public interface ILoginService {

    JwtToken loginJwt(LoginParam loginParam);

    JwtToken refreshToken(JwtToken token);
}
