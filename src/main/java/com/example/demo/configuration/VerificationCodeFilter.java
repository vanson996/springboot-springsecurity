package com.example.demo.configuration;

import com.example.demo.exception.VerificationCodeException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 验证码过滤器
 * @author WYX
 * @date 2021/1/19
 */
public class VerificationCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler = new MyAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 非登录请求不校验验证码
        if (!"/auth/form".equals(httpServletRequest.getRequestURI())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            try {
                verificationCode(httpServletRequest);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (VerificationCodeException e) {
                e.printStackTrace();
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,
                        httpServletResponse, e);

            }
        }


    }

    private void verificationCode(HttpServletRequest httpServletRequest) {
        String requestCode = httpServletRequest.getParameter("captcha");
        HttpSession session = httpServletRequest.getSession();
        String captcha = (String) session.getAttribute("captcha");
        if (StringUtils.hasText(captcha)) {
            // 随手清除验证码，无论失败还是成功。客户端响应在登录失败时刷新验证码
            session.removeAttribute("captcha");
        }
        // 验证不通过抛出异常
        if (StringUtils.isEmpty(requestCode) || StringUtils.isEmpty(captcha) || !requestCode.equals(captcha)) {
            throw new VerificationCodeException();
        }
    }
}
