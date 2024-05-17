package com.xiaosong.music.server.domain.dto;

import com.xiaosong.music.server.domain.Album;
import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.domain.Sheet;
import com.xiaosong.music.server.domain.Singer;
import lombok.Data;

import java.util.List;
@Data
public class SearchDto {
    List<MusicDto> musics;
    List<Sheet> sheets;
    List<Singer> singers;
    List<Album> albums;
}
