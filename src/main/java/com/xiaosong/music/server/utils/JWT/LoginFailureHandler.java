package com.xiaosong.music.server.utils.JWT;

import cn.hutool.json.JSONUtil;
import com.xiaosong.music.server.utils.Captcha.CaptchaException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.xiaosong.music.server.domain.dto.ResultResponse;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        String errorMessage = "用户名或密码错误";
        ResultResponse result;
        if (e instanceof CaptchaException) {
            errorMessage = "验证码错误";
            result = ResultResponse.error(errorMessage);
        } else {
            result = ResultResponse.error(errorMessage);
        }
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
