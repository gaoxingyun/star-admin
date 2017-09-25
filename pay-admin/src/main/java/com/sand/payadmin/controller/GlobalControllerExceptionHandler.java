package com.sand.payadmin.controller;

import com.sand.payadmin.common.contant.ApiContant;
import com.sand.payadmin.common.exception.AuthException;
import com.sand.payadmin.model.pojo.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**
 * 定义全局异常处理
 *
 * @author xy
 *
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = {AuthException.class})
    @ResponseStatus(HttpStatus.OK)
    public RestResult authException(ConstraintViolationException e) {
        return new RestResult<Exception>(ApiContant.RES_CODE_AUTH_FAIL, ApiContant.RES_MSG_AUTH_FAIL, e);
    }


    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public RestResult unknownException(Exception e) {
        return new RestResult<Exception>(ApiContant.RES_CODE_FAIL, ApiContant.RES_MSG_FAIL, e);
    }

}