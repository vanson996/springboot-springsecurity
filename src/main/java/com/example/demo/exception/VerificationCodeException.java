package com.example.demo.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @author WYX
 * @date 2021/1/19
 */
public class VerificationCodeException extends AuthenticationException {

    public VerificationCodeException() {
        super("图形验证码校验失败");
    }
}
