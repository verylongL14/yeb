package com.jzq.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzq.server.util.result.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 *  当未登录或token失效的时候访问接口时，返回的自定义结果
 */
@Controller
public class RestAuthorizationEnrtyPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();

        RespBean respBean = RespBean.error("登录失效，请重新登录");
        respBean.setCode(401);
        printWriter.write(new ObjectMapper().writeValueAsString(respBean));
        printWriter.flush();  // 强推到浏览器
        printWriter.close();

    }
}
