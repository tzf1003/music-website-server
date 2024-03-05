package com.xiaosong.music.server.service;

import com.xiaosong.music.server.domain.Music;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface MusicService extends IService<Music> {
    public List<Music> SearchMusic(String str);
    public List<Music> SearchMusic(String str,Long size);
    public List<Music> SearchMusic(String str,Long page,Long size);
}
