package com.jzq.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzq.server.util.result.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当访问接口没有权限的时候，自定义返回结果
 */
public class RestfulAccessDeniedHandel implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();

        RespBean respBean = RespBean.error("权限不足，请联系管理员");
        respBean.setCode(403);
        printWriter.write(new ObjectMapper().writeValueAsString(respBean));
        printWriter.flush();  // 强推到浏览器
        printWriter.close();
    }
}
