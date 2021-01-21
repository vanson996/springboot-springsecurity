package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author WYX
 * @date 2021/1/19
 */
@Component
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    User findByUserName(String username);
}
