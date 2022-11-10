package com.banxian.myblog.web.interceptor;

import com.banxian.myblog.common.anno.ExcludedPath;
import com.banxian.myblog.common.base.UserInfo;
import com.banxian.myblog.common.util.JwtUtil;
import com.banxian.myblog.exception.TokenInvalidException;
import com.banxian.myblog.support.helper.UserInfoHelper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 登录拦截器
 *
 * @author wangpeng
 * @since 2022-1-12 13:00:30
 */
public class LoginInterceptor implements HandlerInterceptor {

    public static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        try {
            // 可以获取该方法上的自定义注解，然后通过注解来判断该方法是否要被拦截
            // @ExcludedPath 自定义注解，不被拦截
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ExcludedPath excludedPath = method.getAnnotation(ExcludedPath.class);
            if (excludedPath != null) {
                return true;
            }

            // token验证
            String token = request.getHeader("Authorization").substring("Bearer".length() + 1).trim();
            log.info("AuthInterceptor：token is {}", token);
            UserInfo userInfo = JwtUtil.parseToken(token, UserInfo.class);
            if (userInfo == null) {
                throw new TokenInvalidException("未登录或登录状态已失效，请重新登录！");
            }
            UserInfoHelper.set(userInfo);
        } catch (Exception e) {
            throw new TokenInvalidException("未登录或登录状态已失效，请重新登录！");
        }

        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) {
//        log.info("执行完方法之后进行（Controller方法调用之后），但是此时还没有进行视图渲染");
        UserInfoHelper.clear();
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
//        log.info("整个请求都处理完毕，DispatcherServlet也渲染了对应的视图，此时可以做一些清理的工作等等");
    }

}
