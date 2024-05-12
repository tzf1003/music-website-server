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

        return ResultResponse.success();
    }

}
