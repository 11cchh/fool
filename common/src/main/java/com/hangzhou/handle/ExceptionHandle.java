package com.hangzhou.handle;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.hangzhou.base.Result;
import com.hangzhou.constant.ResultEnum;
import com.hangzhou.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @Author: Faye
 * @Data: 2022/9/14 11:20
 */
@RestControllerAdvice
public class ExceptionHandle {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 全局异常处理
     * */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<String> handlerException(Exception e){
        // 业务异常
        if(e instanceof BizException){
            BizException exception = (BizException) e;
            return Result.error(exception.getCode(), exception.getMessage());
        }
        if (e instanceof BindException) {
            BindException exception = (BindException) e;
            if (exception.hasErrors()) {
                List<ObjectError> errors = exception.getAllErrors();
                if (!errors.isEmpty()) {
                    // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                    FieldError fieldError = (FieldError) errors.get(0);
                    return Result.error(ResultEnum.ERROR.getCode(), fieldError.getDefaultMessage());
                }
            }
        }
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = exception.getBindingResult();
            if (bindingResult.hasErrors()) {
                List<ObjectError> errors = bindingResult.getAllErrors();
                if (!errors.isEmpty()) {
                    // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                    FieldError fieldError = (FieldError) errors.get(0);
                    return Result.error(ResultEnum.ERROR.getCode(), fieldError.getDefaultMessage());
                }
            }
        }

        logger.error("【系统异常】：",e);
        return Result.error(ExceptionUtil.stacktraceToString(e));
    }

}
