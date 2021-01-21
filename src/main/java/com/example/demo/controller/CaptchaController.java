package com.example.demo.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author WYX
 * @date 2021/1/19
 */
@Controller
public class CaptchaController {

    @Autowired
    private Producer captchaProducter;


    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置返回类型
        response.setContentType("image/jpeg");
        // 创建验证码文本
        String text = captchaProducter.createText();
        // 将验证码文本设置到session
        request.getSession().setAttribute("captcha", text);
        // 创建验证码图片
        BufferedImage image = captchaProducter.createImage(text);
        // 获取相应输出流
        ServletOutputStream outputStream = response.getOutputStream();
        // 将图片验证码数据写入到响应输出流
        ImageIO.write(image, "jpg", outputStream);
        // 推送并关闭响应输出流
        try {
            outputStream.flush();
        } finally {
            outputStream.close();
        }
    }
}
