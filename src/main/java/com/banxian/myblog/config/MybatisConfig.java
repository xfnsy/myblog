package com.banxian.myblog.config;

import com.banxian.myblog.common.cache.LocalCache;
import com.banxian.myblog.common.idincrementer.CustomIdGenerator;
import com.banxian.myblog.mapper.CommonMapper;
import com.banxian.myblog.support.handle.GlobalMetaObjectHandler;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangpeng on 2020/12/9.
 */
@Configuration
public class MybatisConfig {

    @Resource
    private CommonMapper commonMapper;

    private static final Logger log = LoggerFactory.getLogger(MybatisConfig.class);

    /**
     * 初始化id到缓存中
     */
    @PostConstruct
    public void initIds() {
        log.info("id缓存初始化 ->>>开始");
        List<String> tableNames = commonMapper.showTables();
        for (String tableName : tableNames) {
            int id = commonMapper.selectMaxId(tableName, "id");
            LocalCache.put(CustomIdGenerator.MODULE + tableName, new AtomicInteger(Math.max(id, 100)));
        }
        log.info("id缓存初始化 ->>>结束");
    }

    /**
     * 自定义数据库id生成器
     */
    @Bean
    public IdentifierGenerator getCustomIdGenerator() {
        return new CustomIdGenerator();
    }


    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        log.info("MybatisConfig >>>开始初始化分页插件");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
//        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
//         paginationInterceptor.setOverflow(false);
//        // 设置最大单页限制数量，默认 500 条，-1 不受限制
//         paginationInterceptor.setMaxLimit(500l);
        interceptor.addInnerInterceptor(paginationInterceptor);
        log.info("MybatisConfig >>>成功初始化分页插件");
        return interceptor;

    }

    /**
     * 自定义实现mybatis-plus元注解功能,在插入和修改时自动填充通用参数
     */
    @Bean
    public GlobalMetaObjectHandler globalMetaObjectHandler() {
        log.info("MybatisConfig --->开始初始化自定义参数注入元注解");
        GlobalMetaObjectHandler globalMetaObjectHandler = new GlobalMetaObjectHandler();
        log.info("MybatisConfig --->成功初始化自定义参数注入元注解");
        return globalMetaObjectHandler;
    }
}
