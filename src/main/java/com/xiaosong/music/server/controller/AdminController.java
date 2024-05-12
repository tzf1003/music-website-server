package com.xiaosong.music.server.controller;

import com.xiaosong.music.server.domain.dto.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("admin")
@Api(value = "管理员", tags = "管理员相关的接口", description = "Captcha")
public class AdminController {
    @GetMapping("/home")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员主页", notes = "展示系统信息")
    public ResultResponse adminHome(@RequestHeader("Authorization") String authHeader) {
        //获取总播放量
        //获取歌曲数量
        //获取歌单数量
        //获取用户数量

        //获取系统版本
        //获取服务器信息
        //获取数据库信息
        //获取服务器负载
        //获取数据库负载
        //获取服务器内存

        return ResultResponse.success();
    }

}
