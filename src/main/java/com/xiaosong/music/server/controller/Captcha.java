package com.xiaosong.music.server.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.utils.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@Api(value = "验证码", tags = "验证码相关的接口", description = "Captcha")
public class Captcha {

    @Autowired
    Producer producer;
    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/captcha")
    public ResultResponse Captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = producer.createText();

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        String str = "data:image/jpeg;base64,";

        String base64Img = str + Base64.encode(outputStream.toByteArray());

        redisUtil.hset(Constants.KAPTCHA_SESSION_KEY, key, code, 120);

        return ResultResponse.success(
                MapUtil.builder()
                        .put("userKey", key)
                        .put("captcherImg", base64Img)
                        .build()
        );
    }

}
