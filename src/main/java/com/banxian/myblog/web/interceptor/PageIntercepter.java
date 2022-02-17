package com.banxian.myblog.web.interceptor;

import com.banxian.myblog.support.helper.PageHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 分页封装
 *
 * @author wangpeng
 * @since 2022-1-17 13:18:34
 */
public class PageIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String current = request.getParameter("current");
        String size = request.getParameter("size");
        if (StringUtils.hasText(current) && StringUtils.hasText(size)) {
            Page page = new Page(Long.parseLong(current), Long.parseLong(size));
            PageHelper.set(page);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        PageHelper.remove();
    }
}
