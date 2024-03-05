package com.xiaosong.music.server.service;

import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.domain.Sheet;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SheetService extends IService<Sheet> {
    public List<Sheet> SearchSheet(String str);
    public List<Sheet> SearchSheet(String str,Long size);
    public List<Sheet> SearchSheet(String str,Long page,Long size);

}
