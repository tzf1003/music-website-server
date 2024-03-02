package com.xiaosong.music.server.config.JWT;

import cn.hutool.json.JSONUtil;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * JWT注销成功处理器。
 * 实现Spring Security的LogoutSuccessHandler接口，用于处理用户注销成功后的逻辑。
 */
@Component
public class JWTLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils; // 注入JwtUtils工具类，用于JWT操作。

    /**
     * 当用户成功注销时调用此方法。
     *
     * @param httpServletRequest  请求对象
     * @param httpServletResponse 响应对象
     * @param authentication      当前的认证信息
     * @throws IOException 如果发生输入输出异常
     * @throws ServletException 如果发生Servlet异常
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        // 如果当前用户已经认证，则执行注销操作。
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
        }

        // 设置响应类型为JSON，并指定字符集为UTF-8。
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        // 清除JWT头信息。
        httpServletResponse.setHeader(jwtUtils.getHeader(), "");

        // 创建注销成功的响应结果对象。
        ResultResponse result = ResultResponse.success("SuccessLogout");

        // 将结果对象转换为JSON字符串，并写入响应流。
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush(); // 刷新输出流，确保数据完全发送至客户端。
        outputStream.close(); // 关闭输出流。
    }
}
