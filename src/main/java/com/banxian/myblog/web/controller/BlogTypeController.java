package com.banxian.myblog.web.controller;


import com.banxian.myblog.common.util.RandomPwdUtil;
import com.banxian.myblog.domain.BlogType;
import com.banxian.myblog.service.IBlogTypeService;
import com.banxian.myblog.support.helper.PageHelper;
import com.banxian.myblog.web.view.JsonView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 博客类型 前端控制器
 * </p>
 *
 * @author wangpeng
 * @since 2022-01-13
 */
@RestController
@RequestMapping("/blogType")
public class BlogTypeController {

    @Resource
    private IBlogTypeService blogTypeService;

    @GetMapping("/list")
    public JsonView list() {
        return JsonView.suc("list", blogTypeService.list());
    }

    @GetMapping("/page")
    public JsonView page(String typeName) {
        QueryWrapper<BlogType> qw = new QueryWrapper<>();
        qw.eq("deleted", '0');
        if (StringUtils.hasText(typeName)) {
            qw.like("type_name", typeName);
        }
        return JsonView.suc("page", blogTypeService.page(PageHelper.get(), qw));
    }

    @GetMapping("/{id}")
    public JsonView get(@PathVariable("id") Integer id) {
        return JsonView.suc("blogType", blogTypeService.getById(id));
    }

    @PostMapping("")
    public JsonView add(@RequestBody BlogType blogType) {
        blogTypeService.save(blogType);
        return JsonView.sucAdd();
    }

    @PutMapping("/{id}")
    public JsonView update(@PathVariable("id") Integer id, @RequestBody BlogType blogType) {
        BlogType oldType = blogTypeService.getById(id);
        oldType.setTypeName(blogType.getTypeName());
        oldType.setDetail(blogType.getDetail());
        oldType.setValid(blogType.getValid());
        oldType.setUpdateAt(LocalDateTime.now());
        blogTypeService.updateById(oldType);
        return JsonView.sucUpdate();
    }

    @DeleteMapping("/{id}")
    public JsonView delete(@PathVariable("id") Integer id) {
        BlogType oldType = blogTypeService.getById(id);
        oldType.setDeleted("1");
        blogTypeService.updateById(oldType);
        return JsonView.sucDel();
    }
}
