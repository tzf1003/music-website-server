package com.xiaosong.music.server.mapper;

import com.xiaosong.music.server.domain.Album;
import com.xiaosong.music.server.domain.Music;
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
    @Select("SELECT m.* FROM xs_music m JOIN xs_singer_music sm ON m.id = sm.music WHERE sm.singer = #{singerId}")
    List<Music> selectMusicBySingerId(Integer singerId);

}




