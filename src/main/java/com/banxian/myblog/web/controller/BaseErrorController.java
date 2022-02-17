package com.banxian.myblog.web.controller;

import com.banxian.myblog.exception.UndefinedException;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "error")
public class BaseErrorController extends BasicErrorController {

    public BaseErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
    }


    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        throw new UndefinedException("系统异常!");
    }
}
