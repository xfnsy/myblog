package com.banxian.myblog.common.idincrementer;

import com.banxian.myblog.common.cache.LocalCache;
import com.banxian.myblog.common.util.CaseUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * mybatis-plus 自定义逐渐生成器
 *
 * @author wangpeng
 * @since 2022-1-19 15:41:16
 */
public class CustomIdGenerator implements IdentifierGenerator {

    public static final String MODULE = "ID_INCR_";


    @Override
    public Number nextId(Object entity) {
        //可以将当前传入的class全类名转为表名
        String tableName = CaseUtil.camel2Underline(entity.getClass().getSimpleName());
        //返回生成的id值即可.
        return Objects.requireNonNull(LocalCache.get(MODULE + tableName, AtomicInteger.class)).incrementAndGet();
    }
}