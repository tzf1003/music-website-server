package com.xiaosong.music.server.mapper;

import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.domain.SheetMusic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.xiaosong.music.server.domain.SheetMusic
 */
public interface SheetMusicMapper extends BaseMapper<SheetMusic> {
    @Select("SELECT m.* FROM xs_music m JOIN xs_sheet_music sm ON m.id = sm.music WHERE sm.sheet = #{sheetId}")
    List<Music> selectMusicBySheetId(Integer sheetId);
}