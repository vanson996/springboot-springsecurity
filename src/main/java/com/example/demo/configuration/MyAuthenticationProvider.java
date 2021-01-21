package com.example.demo.configuration;

import com.example.demo.exception.VerificationCodeException;
import com.example.demo.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author WYX
 * @date 2021/1/21
 */
@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    // 构造方法注入
    public MyAuthenticationProvider(UserService userService, CustomPasswordEncoder customPasswordEncoder) {
        this.setUserDetailsService(userService);
        this.setPasswordEncoder(customPasswordEncoder);
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 获取详细信息
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        // 一旦发现验证码信息不正确立即抛出相应异常信息
        if (!details.getImageCodeIsRight()) {
            throw new VerificationCodeException();
        }
        // 调用父类方法完成密码验证
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
