package com.xiaosong.music.server.controller.admin;

import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.SystemInfoDto;
import com.xiaosong.music.server.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    SystemService systemService;
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员主页", notes = "展示系统信息")
    public ResultResponse adminHome(@RequestHeader("Authorization") String authHeader) {
        SystemInfoDto systemInfo = new SystemInfoDto();
        // 假设有方法来获取以下信息
        systemInfo.setMusicCount(systemService.getMusicCount());
        systemInfo.setSheetCount(systemService.getSheetCount());
        systemInfo.setUserCount(systemService.getUserCount());
        systemInfo.setSystemVersion(systemService.getSystemVersion());
        systemInfo.setServerInfo(systemService.getServerInfo());
        systemInfo.setDatabaseInfo(systemService.getDatabaseInfo());
        systemInfo.setServerLoad(systemService.getServerLoad());
        systemInfo.setDatabaseLoad(systemService.getDatabaseLoad());
        systemInfo.setServerMemory(systemService.getServerMemory());

        return ResultResponse.success(systemInfo);

    }

}
