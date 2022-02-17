package com.banxian.myblog.service.impl;

import com.banxian.myblog.domain.Blog;
import com.banxian.myblog.mapper.BlogMapper;
import com.banxian.myblog.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author wangpeng
 * @since 2022-01-20
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

}
