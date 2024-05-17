package com.xiaosong.music.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosong.music.server.domain.Album;
import com.xiaosong.music.server.domain.AlbumMusic;
import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.service.AlbumMusicService;
import com.xiaosong.music.server.mapper.AlbumMusicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class AlbumMusicServiceImpl extends ServiceImpl<AlbumMusicMapper, AlbumMusic>
implements AlbumMusicService{
    @Autowired
    AlbumMusicMapper albumMusicMapper;

    @Override
    public List<Album> selectAlbumByMusicId(Integer musicId) {
        return albumMusicMapper.selectAlbumByMusicId(musicId);
    }

    @Override
    public List<Music> selectMusicByAlbum(Integer id) {
        List<Music> music = albumMusicMapper.selectMusicByAlbumId(id);
        return music;
    }
}




