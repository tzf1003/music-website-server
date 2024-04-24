package com.xiaosong.music.server.service;

import com.xiaosong.music.server.domain.Singer;
import com.xiaosong.music.server.domain.SingerMusic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SingerMusicService extends IService<SingerMusic> {
    public List<Singer> selectSingerByMusicId(Integer musicId);
}
