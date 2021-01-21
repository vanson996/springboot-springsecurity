package com.example.demo.configuration;

import com.example.demo.service.UserService;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;

/**
 * @author WYX
 * @date 2021/1/15
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/app/api/**", "/captcha.jpg").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                // 指定登录页
                .loginPage("/myLogin.html")
                // 指定处理登录请求的路径
                .loginProcessingUrl("/auth/form").permitAll()
                .defaultSuccessUrl("/")
                .failureHandler(new MyAuthenticationFailureHandler())
                .and()
                .sessionManagement()
                .maximumSessions(1);
        // 将验证码过滤器添加到 springsecurity过滤器链中，并添加在验证用户信息之前
        http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    // 自定义配置认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }


    /**
     * 配置一个 kaptcha 图形验证码实例
     */
    @Bean
    public Producer kaptcha() {
        //配置图形验证码的基本参数
        Properties properties = new Properties();
        //图片宽度
        properties.setProperty("kaptcha.image.width", "150");
        //图片长度
        properties.setProperty("kaptcha.image.height", "50");
        //字符集
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        //字符长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        Config config = new Config(properties);
        // 使用默认的图形验证实现，也可以自定义实现
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
