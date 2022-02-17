package com.banxian.myblog.config;

import com.banxian.myblog.web.interceptor.AuthInterceptor;
import com.banxian.myblog.web.interceptor.PageIntercepter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring拦截器
 *
 * @author wangpeng
 * @since 2022-1-10 13:10:04
 */
@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 实现WebMvcConfigurer不会导致静态资源被拦截
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**").order(1);
        registry.addInterceptor(new PageIntercepter()).addPathPatterns("/*/page").order(2);
    }

}
