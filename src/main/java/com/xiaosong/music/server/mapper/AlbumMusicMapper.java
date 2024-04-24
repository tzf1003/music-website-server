package com.xiaosong.music.server.mapper;

import com.xiaosong.music.server.domain.Album;
import com.xiaosong.music.server.domain.AlbumMusic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.xiaosong.music.server.domain.AlbumMusic
 */
public interface AlbumMusicMapper extends BaseMapper<AlbumMusic> {
    @Select("SELECT a.* FROM xs_album a JOIN xs_album_music am ON a.id = am.album WHERE am.music = #{musicId}")
    List<Album> selectAlbumByMusicId(Integer musicId);

}




