package com.xiaosong.music.server.mapper;

import com.xiaosong.music.server.domain.Album;
import com.xiaosong.music.server.domain.Singer;
import com.xiaosong.music.server.domain.SingerMusic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.xiaosong.music.server.domain.SingerMusic
 */
public interface SingerMusicMapper extends BaseMapper<SingerMusic> {
    @Select("SELECT s.* FROM xs_singer s JOIN xs_singer_music sm ON s.id = sm.singer WHERE sm.music = #{musicId}")
    List<Singer> selectSingerByMusicId(Integer musicId);
}




