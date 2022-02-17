package com.banxian.myblog.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;




        // cookie 登录状态校验
//        Cookie[] cookies = request.getCookies();
//        if (cookies == null || cookies.length < 2) {
//            response.sendRedirect(request.getContextPath() + "/toLogin");
//            return;
//        }
//        Cookie account = Arrays.stream(cookies).filter(c -> c.getName().equals("account")).findFirst().get();
//        Cookie auth = Arrays.stream(cookies).filter(c -> c.getName().equals("auth")).findFirst().get();
//        if (!MD5Util.valid(account.getValue() + Constants.MD5_SALT, auth.getValue())) {
//            response.sendRedirect(request.getContextPath() + "/toLogin");
//            return;
//        }

        chain.doFilter(req, rep);
    }

    @Override
    public void destroy() {

    }



}
