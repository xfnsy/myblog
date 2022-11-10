package com.banxian.myblog.web.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filter的一些全局设置
 *
 * @author wangpeng
 * @since 2021/1/15 10:39
 */
public class GlobalFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;

        // 指定了该响应的资源是否被允许与给定的origin共享，可以设置为:
        // 1. * 对于不需具备凭证（credentials）的请求，服务器会以“*”作为通配符，从而允许所有域都具有访问资源的权限
        // 2. https://developer.mozilla.org 指定域，此时必须在Vary响应头中包含次Origin,代表为不同的 Origin 缓存不同的资源 ;
        // 注意：当响应的是附带身份凭证的请求时，也就是前端设置req.withCredentials = true，此时服务端必须明确 Access-Control-Allow-Origin|HEADERS|METHODS 的值，而不能使用通配符“*”。
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        response.setHeader(HttpHeaders.VARY, "Origin");
        // 允许的请求头
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Content-Disposition, Authorization");
        // 允许的请求方法
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, DELETE, PUT");
        // 允许是否基于HTTP Cookies和 HTTP认证信息发送身份凭证,前端需设置req.withCredentials = true;
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        // 这个响应头表示预检请求的返回结果（即 Access-Control-Allow-Methods和Access-Control-Allow-Headers提供的信息）可以被缓存多久,s为单位
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        // 哪些头部可以作为响应的一部分暴露给外部
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");

        // csp前后端项目分离设置响应头没啥用
        response.setHeader("Content-Security-Policy", "default-src 'self'");
        // 安全设置的请求头
        response.setHeader("x-frame-options", "DENY");// DENY或者SAMEORIGIN
        response.setHeader("X-xss-protection", "1;mode=block");
        response.setHeader("X-Content-Type-Options", "nosniff");

        // 如果是OPTIONS则结束请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.OK.value());
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
