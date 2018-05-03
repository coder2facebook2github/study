package com.spring.boot.study.controller;

import com.spring.boot.study.common.Constants;
import com.spring.boot.study.model.master.vo.LoginVo;
import com.spring.boot.study.model.master.vo.RandomImage;
import com.spring.boot.study.service.LoginService;
import com.utils.JedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ImageValidController {

    @Autowired
    private JedisService jedisService;
    @Autowired
    private LoginService loginService;

    @Value("${token.image.timeout}")
    private int timeout;

    @RequestMapping(value = "/image/random/noLogin", method = RequestMethod.GET)
    public void createImage(@NotBlank(message = "token不能为空") String token, HttpServletResponse response) throws IOException {
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", -1);
        response.setContentType("image/jpeg");
        RandomImage randomImage = new RandomImage();
        Map<String, Object> randomCodeMap = new HashMap<>();
        randomCodeMap.put("code", randomImage.getRandomCode());
        randomCodeMap.put("count", 0);
        jedisService.set(Constants.IMAGE_TOKEN + token, randomCodeMap, timeout);
        ServletOutputStream outputStream;
        outputStream = response.getOutputStream();
        ImageIO.write(randomImage.getImage(), "jpg", outputStream);
        outputStream.close();
    }

    @ResponseBody
    @RequestMapping(value = "/valid/image/code/noLogin", method = RequestMethod.POST)
    public Map<String, Object> validCode(@NotBlank(message = "token错误") String token,
                                         @NotBlank(message = "验证码错误") String code) {
        Map<String, Object> validResult = new HashMap<>();
        LoginVo validVo = new LoginVo();
        validVo.setCode(code);
        validVo.setToken(token);
        Map<String, Object> checkResult = loginService.validateImageCode(validVo);
        if(!Constants.SUCCESS.equals(checkResult.get(Constants.MESSAGE))) {
            validResult.put("error", checkResult.get(Constants.MESSAGE));
        }
        return validResult;
    }
}
