package com.jzq.server.exception;

import com.jzq.server.util.result.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public RespBean mySqlException(SQLException e) {
        if(e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据存在关联，操作失败!");
        }
        return RespBean.error("数据库错误，请联系管理员");
    }
}
