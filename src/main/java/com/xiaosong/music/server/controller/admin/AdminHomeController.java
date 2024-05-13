package com.xiaosong.music.server.controller.admin;

import com.xiaosong.music.server.domain.dto.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("admin/home")
@Api(value = "后台管理", tags = "主页相关的接口", description = "MusicAdmin")
public class AdminHomeController {
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
