package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WYX
 * @date 2021/1/18
 */
@RestController
@RequestMapping("/app/api")
public class AppController {

    @GetMapping("hello")
    public String hello() {
        return "hello，app";
    }
}
