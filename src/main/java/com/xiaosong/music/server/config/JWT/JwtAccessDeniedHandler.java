// 定义包路径
package com.xiaosong.music.server.config.JWT;

// 导入所需的类和包
import cn.hutool.json.JSONUtil;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 自定义访问拒绝处理器。
 * 实现Spring Security的AccessDeniedHandler接口，用于自定义访问被拒绝时的处理逻辑。
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 处理访问被拒绝时的逻辑。
     *
     * @param httpServletRequest  请求对象
     * @param httpServletResponse 响应对象
     * @param e                   访问拒绝异常
     * @throws IOException      如果发生输入输出异常
     * @throws ServletException 如果发生Servlet异常
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        // 设置响应的内容类型为JSON，并且指定字符集为UTF-8
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 设置HTTP响应状态码为403，代表访问被禁止
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 获取响应的输出流，用于向客户端发送响应数据
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        // 创建一个表示错误的ResultResponse对象，包含了拒绝访问的错误信息
        ResultResponse result = ResultResponse.error(e.getMessage());

        // 将ResultResponse对象转换为JSON字符串，并写入响应的输出流
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        // 刷新输出流，确保数据被发送到客户端
        outputStream.flush();
        // 关闭输出流
        outputStream.close();
    }
}
