package com.example.demo.configuration;

import org.springframework.stereotype.Component;

/**
 * 自定义用户密码校验
 *
 * @author WYX
 * @date 2021/1/18
 */
@Component
public class CustomPasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
