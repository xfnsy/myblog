package com.banxian.myblog.support.handle;

import com.banxian.myblog.config.MybatisConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * 自定义实现mybatis-plus元注解功能,在插入和修改时自动填充通用参数,
 * 注意:需要和Entity实体对象上的注解TableField结合使用
 *
 * @author wangpeng
 * @since 2021/1/4 15:57
 */
public class GlobalMetaObjectHandler implements MetaObjectHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalMetaObjectHandler.class);

    /**
     * 新增时对象属性插入，全局的
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createAt", LocalDateTime.class, LocalDateTime.now()); // 创建时间
        this.strictInsertFill(metaObject, "updateAt", LocalDateTime.class, LocalDateTime.now()); // 修改时间,开始和修改时间一样

    }

    /**
     * 修改时对象属性插入，全局的
     * strictInsertFill和strictUpdateFill需满足对象值为空和设置的值不为空时才会设置，
     * 无法满足修改一次，设置一次修改时间和修改人的需求，所以改为setFieldValByName
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateAt", LocalDateTime.now(), metaObject);// 修改时间
    }

}
