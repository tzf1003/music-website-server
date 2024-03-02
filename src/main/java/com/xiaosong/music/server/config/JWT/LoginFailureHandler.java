package com.xiaosong.music.server.config.JWT;

import cn.hutool.json.JSONUtil;
import com.xiaosong.music.server.config.Captcha.CaptchaException;

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

/**
 * 自定义登录失败处理器。
 * 实现Spring Security的AuthenticationFailureHandler接口，用于自定义登录失败时的处理逻辑。
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * 在认证失败时调用。
     *
     * @param httpServletRequest  请求对象
     * @param httpServletResponse 响应对象
     * @param e                   认证失败时抛出的异常
     * @throws IOException      如果发生输入输出异常
     * @throws ServletException 如果发生Servlet异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 设置响应的内容类型为JSON，并且指定字符集为UTF-8
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        String errorMessage = "用户名或密码错误"; // 默认错误消息
        ResultResponse result;

        // 判断异常类型，如果是验证码异常，则修改错误消息
        if (e instanceof CaptchaException) {
            errorMessage = "验证码错误";
        }

        // 创建错误响应对象
        result = ResultResponse.error(errorMessage);

        // 将错误响应对象转换为JSON字符串，并写入响应输出流
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush(); // 刷新输出流
        outputStream.close(); // 关闭输出流
    }
}
