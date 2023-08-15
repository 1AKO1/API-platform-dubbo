package com.bagamao.apicommon.exception;

import com.bagamao.apicommon.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public JsonResult<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return JsonResult.err().code(e.getCode()).msg(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public JsonResult<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return JsonResult.err().code(JsonResult.SYS_ERROR).msg("系统错误");
    }
}
