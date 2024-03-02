package com.xiaosong.music.server.config.JWT;

import cn.hutool.json.JSONUtil;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 自定义JWT认证失败处理器
 * 实现AuthenticationEntryPoint接口，用于处理认证过程中的异常，如未登录、token失效等
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当用户请求需要认证的资源，但未通过认证时，将调用此方法
     *
     * @param httpServletRequest 请求对象
     * @param httpServletResponse 响应对象
     * @param e 认证过程中抛出的异常
     * @throws IOException 可能抛出的IO异常
     * @throws ServletException 可能抛出的Servlet异常
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 设置响应的内容类型和状态码
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 获取响应输出流
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        // 创建错误响应对象并序列化为JSON格式输出
        ResultResponse result = ResultResponse.error("请先登录");

        // 写入响应数据并关闭输出流
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
