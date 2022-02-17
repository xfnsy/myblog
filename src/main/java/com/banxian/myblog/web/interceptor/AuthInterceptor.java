package com.banxian.myblog.web.interceptor;

import com.banxian.myblog.common.anno.ExcludedPath;
import com.banxian.myblog.common.base.UserInfo;
import com.banxian.myblog.common.util.JwtUtil;
import com.banxian.myblog.exception.TokenException;
import com.banxian.myblog.support.helper.TokenHelper;
import com.banxian.myblog.support.helper.UserInfoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 权限拦截器
 *
 * @author wangpeng
 * @since 2022-1-12 13:00:30
 */
public class AuthInterceptor implements HandlerInterceptor {

    public static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 可以获取该方法上的自定义注解，然后通过注解来判断该方法是否要被拦截
        // @ExcludedPath 自定义注解，不被拦截
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        ExcludedPath excludedPath = method.getAnnotation(ExcludedPath.class);
        if (excludedPath != null) {
            return true;
        }

        // token验证
        String token = request.getHeader("Authorization").split(" ")[1];
        log.info("AuthInterceptor：token is {}", token);
        UserInfo userInfo = JwtUtil.getData(token, UserInfo.class);
        if (userInfo == null) {
            log.error("token:{} valid failed", token);
            throw new TokenException("token已经失效或无效，请重新登录");
        }
        if(!TokenHelper.checkTokenValid(token)){
            log.error("token:{} logout ", token);
            throw new TokenException("token已经注销，请重新登录");
        }
        UserInfoHelper.set(userInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//        log.info("执行完方法之后进行（Controller方法调用之后），但是此时还没有进行视图渲染");
        UserInfoHelper.clear();
        TokenHelper.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        log.info("整个请求都处理完毕，DispatcherServlet也渲染了对应的视图，此时可以做一些清理的工作等等");
    }

}
