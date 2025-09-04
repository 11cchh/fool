package com.hangzhou.controller;

import com.hangzhou.base.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制层
 *
 * @Author Faye
 * @Date 2025/3/17 15:37
 */
@RestController
@RequestMapping("api")
public class CommonController {
    @RequestMapping(value = "/**", method = {RequestMethod.POST,RequestMethod.GET})
    public DeferredResult<Result> openApi(HttpServletRequest request) {
        DeferredResult<Result> result = new DeferredResult<>();

        return result;
    }
}
