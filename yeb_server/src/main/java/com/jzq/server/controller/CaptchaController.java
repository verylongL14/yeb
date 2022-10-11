package com.jzq.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码
 */
@Api(tags = "验证码类接口管理")
@RestController
@RequestMapping("/api")
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "验证码")
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void capacha(HttpServletRequest request, HttpServletResponse response) {
        // 定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
                response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        //-------------------生成验证码 begin --------------------------

        // 获取验证码的文本内容
        String text = defaultKaptcha.createText();
        System.out.println("验证码：" + text);

        // 将验证码放到session
        request.getSession().setAttribute("captcha", text);

        // 根据文本验证码内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream servletOutputStream = null;
        try {
            servletOutputStream = response.getOutputStream();
            // 输出流输出图片， 格式为jpg
            ImageIO.write(image, "jpg", servletOutputStream);
            // 发送到浏览器
            servletOutputStream.flush();

        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (servletOutputStream != null) {
                try {
                    servletOutputStream.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //-------------------生成验证码 end ----------------------------
    }
}
