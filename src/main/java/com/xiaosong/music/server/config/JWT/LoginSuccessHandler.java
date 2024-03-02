package com.xiaosong.music.server.config.JWT;

import cn.hutool.json.JSONUtil;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 自定义登录成功处理器。
 * 实现Spring Security的AuthenticationSuccessHandler接口，用于自定义登录成功时的处理逻辑。
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils; // 注入JwtUtils，用于生成JWT。

    /**
     * 在认证成功时调用。
     *
     * @param httpServletRequest  请求对象
     * @param httpServletResponse 响应对象
     * @param authentication      当前的认证信息
     * @throws IOException      如果发生输入输出异常
     * @throws ServletException 如果发生Servlet异常
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 设置响应的内容类型为JSON，并且指定字符集为UTF-8
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        // 生成JWT，并将其设置在HTTP响应头中
        String jwt = jwtUtils.generateToken(authentication.getName());
        httpServletResponse.setHeader(jwtUtils.getHeader(), jwt);

        // 创建登录成功的响应结果
        ResultResponse resultResponse = ResultResponse.success("SuccessLogin");

        // 将成功响应结果转换为JSON字符串，并写入响应输出流
        outputStream.write(JSONUtil.toJsonStr(resultResponse).getBytes(StandardCharsets.UTF_8));
        outputStream.flush(); // 刷新输出流，确保数据发送
        outputStream.close(); // 关闭输出流
    }
}
