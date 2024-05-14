package com.xiaosong.music.server.domain.dto;

import lombok.Data;

@Data
public class SystemInfoDto {
    private Integer musicCount;
    private Integer sheetCount;
    private Integer userCount;
    private String systemVersion;
    private String serverInfo;
    private String databaseInfo;
    private Double serverLoad;
    private Double databaseLoad;
    private Double serverMemory;
}
