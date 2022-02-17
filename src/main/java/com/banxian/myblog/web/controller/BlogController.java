package com.banxian.myblog.web.controller;


import com.banxian.myblog.domain.Blog;
import com.banxian.myblog.domain.saveparam.BlogParam;
import com.banxian.myblog.service.IBlogService;
import com.banxian.myblog.support.helper.PageHelper;
import com.banxian.myblog.web.view.JsonView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 博客表 前端控制器
 * </p>
 *
 * @author wangpeng
 * @since 2022-01-20
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private IBlogService blogService;

    @GetMapping("/list")
    public JsonView list() {
        return JsonView.suc("list", blogService.list());
    }

    @GetMapping("/page")
    public JsonView page() {
        QueryWrapper<Blog> qw = new QueryWrapper<>();
        return JsonView.suc("page", blogService.page(PageHelper.get(), qw));
    }

    @GetMapping("/{id}")
    public JsonView get(@PathVariable("id") Integer id) {
        return JsonView.suc("blog", blogService.getById(id));
    }

    @PostMapping("")
    public JsonView add(@RequestBody Blog blog) {
        blogService.save(blog);
        return JsonView.suc("id", blog.getBlogTypeId());
    }

    @PutMapping("/{id}")
    public JsonView update(@PathVariable("id") Integer id, @RequestBody BlogParam blogParam) {
        Blog oldBlog = blogService.getById(id);
        BeanUtils.copyProperties(blogParam, oldBlog);
        blogService.updateById(oldBlog);
        return JsonView.sucUpdate();
    }

    @DeleteMapping("/{id}")
    public JsonView delete(@PathVariable("id") Integer id) {
        blogService.removeById(id);
        return JsonView.sucDel();
    }
}
