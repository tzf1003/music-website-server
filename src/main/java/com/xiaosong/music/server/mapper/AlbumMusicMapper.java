package com.xiaosong.music.server.mapper;

import com.xiaosong.music.server.domain.Album;
import com.xiaosong.music.server.domain.AlbumMusic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaosong.music.server.domain.Music;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.xiaosong.music.server.domain.AlbumMusic
 */
public interface AlbumMusicMapper extends BaseMapper<AlbumMusic> {
    @Select("SELECT a.* FROM xs_album a JOIN xs_album_music am ON a.id = am.album WHERE am.music = #{musicId}")
    List<Album> selectAlbumByMusicId(Integer musicId);
    @Select("SELECT m.* FROM xs_music m JOIN xs_album_music sm ON m.id = sm.music WHERE sm.album = #{sheetId}")
    List<Music> selectMusicByAlbumId(Integer sheetId);
}




