package com.xiaosong.music.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosong.music.server.domain.Singer;
import com.xiaosong.music.server.domain.SingerMusic;
import com.xiaosong.music.server.service.SingerMusicService;
import com.xiaosong.music.server.mapper.SingerMusicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SingerMusicServiceImpl extends ServiceImpl<SingerMusicMapper, SingerMusic>
implements SingerMusicService{
    @Autowired
    SingerMusicMapper singerMusicMapper;

    @Override
    public List<Singer> selectSingerByMusicId(Integer musicId) {
        return singerMusicMapper.selectSingerByMusicId(musicId);
    }

    @Override
    public String getSingerStrByMusic(Integer musicId) {
        List<Singer> singers = selectSingerByMusicId(musicId);
        String singerStr = null;
        for (int i = 0; i < singers.size(); i++) {
            Singer singer = singers.get(i);
            singerStr += singer.getName();
            if (i < singers.size() - 1) {
                singerStr += "，";
            }
        }
        return singerStr;
    }
}




