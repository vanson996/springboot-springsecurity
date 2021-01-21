package com.example.demo.configuration;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author WYX
 * @date 2021/1/21
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean imageCodeIsRight;

    public boolean getImageCodeIsRight() {
        return this.imageCodeIsRight;
    }

    /**
     * 补充用户提交的验证码和session保存的验证码
     */
    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String imageCode = request.getParameter("captcha");
        HttpSession session = request.getSession();
        String savedCode = (String) session.getAttribute("captcha");
        if (StringUtils.hasText(savedCode)) {
            session.removeAttribute("captcha");
            if (StringUtils.hasText(imageCode) && imageCode.equals(savedCode)) {
                this.imageCodeIsRight = true;
            }
        }
    }
}
