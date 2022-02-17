package com.banxian.myblog.web.controller;


import com.banxian.myblog.common.anno.ExcludedPath;
import com.banxian.myblog.web.view.JsonView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @ExcludedPath
    @GetMapping({"/index"})
    @ResponseBody
    public JsonView index() {
        return JsonView.suc("hello，success！");
    }

    @ExcludedPath
    @GetMapping({"/"})
    public ModelAndView test(ModelAndView mv) {
        mv.setViewName("index");
        return mv;
    }
}

