package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author WYX
 * @date 2021/1/18
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {


        // 基于数据库查询校验用户信息
        User user = userMapper.findByUserName(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));
        return user;
    }
}
