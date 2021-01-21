package com.example.demo.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证失败消息拦截
 * @author WYX
 * @date 2021/1/21
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write("{\"errorcode：401，name：\"" + e.getCause() + "，message：\"" + e.getMessage() + "\"}");
    }
}
