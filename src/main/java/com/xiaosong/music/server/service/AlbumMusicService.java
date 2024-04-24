package com.xiaosong.music.server.service;

import com.xiaosong.music.server.domain.Album;
import com.xiaosong.music.server.domain.AlbumMusic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface AlbumMusicService extends IService<AlbumMusic> {
    public List<Album> selectAlbumByMusicId(Integer musicId);
}
