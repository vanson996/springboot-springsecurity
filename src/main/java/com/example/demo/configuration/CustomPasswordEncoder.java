package com.example.demo.configuration;

/**
 * 自定义用户密码校验
 *
 * @author WYX
 * @date 2021/1/18
 */
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
