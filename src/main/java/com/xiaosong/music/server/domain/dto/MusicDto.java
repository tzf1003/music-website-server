package com.xiaosong.music.server.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xiaosong.music.server.domain.Album;
import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.domain.Singer;
import com.xiaosong.music.server.service.AlbumMusicService;
import com.xiaosong.music.server.service.SingerMusicService;
import com.xiaosong.music.server.service.impl.AlbumMusicServiceImpl;
import com.xiaosong.music.server.service.impl.SingerMusicServiceImpl;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class MusicDto {
    private Music music;
    private Album albumObject;
    private List<Singer> singerObject;
    private Boolean isLike;

}
