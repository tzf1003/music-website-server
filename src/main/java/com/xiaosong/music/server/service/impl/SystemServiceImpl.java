package com.xiaosong.music.server.service.impl;

import com.xiaosong.music.server.service.MusicService;
import com.xiaosong.music.server.service.SheetService;
import com.xiaosong.music.server.service.SystemService;
import com.xiaosong.music.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private MusicService musicService;

    @Autowired
    private SheetService sheetService;

    @Autowired
    private UserService userService;

    public Integer getMusicCount() {
        return Math.toIntExact(musicService.count());
    }

    public Integer getSheetCount() {
        return Math.toIntExact(sheetService.count());
    }

    public Integer getUserCount() {
        return Math.toIntExact(userService.count());
    }

    public String getSystemVersion() {
        // 这里简化处理，实际可能需要读取配置文件或常量
        return "1.0.0";
    }

    public String getServerInfo() {
        // 模拟服务器信息
        return "Apache Tomcat/9.0.41";
    }

    public String getDatabaseInfo() {
        // 模拟数据库信息
        return "MySQL 8.0.22";
    }

    public Double getServerLoad() {
        // 模拟服务器负载
        return 0.5;
    }

    public Double getDatabaseLoad() {
        // 模拟数据库负载
        return 0.3;
    }

    public Double getServerMemory() {
        // 模拟服务器内存使用情况
        return 2048.00; // 假设为2GB
    }
}
