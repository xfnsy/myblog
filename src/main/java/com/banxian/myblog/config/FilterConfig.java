package com.banxian.myblog.config;


import com.banxian.myblog.web.filter.GlobalFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * servlet filter 配置
 */
@Configuration
public class FilterConfig {

    private static final Logger log = LoggerFactory.getLogger(FilterConfig.class);

    @Bean
    public FilterRegistrationBean<GlobalFilter> registerLoginFilter() {
        log.info("FilterConfig->>> 初始化全局filter开始");
        FilterRegistrationBean<GlobalFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new GlobalFilter());
        registration.addUrlPatterns("/*");// 过滤路径
        registration.setName("GlobalFilter");// 名称,类似于filter-name
        registration.setOrder(1);  //值越小，Filter越靠前。
        log.info("FilterConfig->>> 初始化全局filter结束");
        return registration;
    }

    // 多个filter可继续在这里注册
//    @Bean
//    public FilterRegistrationBean registerAuthFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new AuthFilter());
//        registration.addUrlPatterns("/*");// 需要过滤的路径
//        registration.setName("authFilter");// 名称,类似于filter-name
//        registration.setOrder(2);  //值越小，Filter越靠前。
//        return registration;
//    }


}
