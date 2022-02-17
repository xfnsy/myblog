package com.banxian.myblog.service.impl;

import com.banxian.myblog.domain.BlogType;
import com.banxian.myblog.mapper.BlogTypeMapper;
import com.banxian.myblog.service.IBlogTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客类型 服务实现类
 * </p>
 *
 * @author wangpeng
 * @since 2022-01-13
 */
@Service
public class BlogTypeServiceImpl extends ServiceImpl<BlogTypeMapper, BlogType> implements IBlogTypeService {

}
