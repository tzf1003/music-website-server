package com.xiaosong.music.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaosong.music.server.domain.User;
import com.xiaosong.music.server.domain.dto.SystemInfoDto;

public interface SystemService{
    public Integer getMusicCount();
    public Integer getSheetCount();

    public Integer getUserCount();
    public String getServerInfo();
    public String getDatabaseInfo();
    public Double getServerLoad();
    public Double getDatabaseLoad();
    public Double getServerMemory();
    public String getSystemVersion();

}
