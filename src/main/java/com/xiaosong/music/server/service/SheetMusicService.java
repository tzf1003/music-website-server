package com.xiaosong.music.server.service;

import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.domain.SheetMusic;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 */
public interface SheetMusicService extends IService<SheetMusic> {
    public List<Music> selectMusicBySheet(Integer sheetId);
    public boolean isLike(Integer sheetId,Integer musicId);
}
