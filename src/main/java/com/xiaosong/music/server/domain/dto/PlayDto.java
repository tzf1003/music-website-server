package com.xiaosong.music.server.domain.dto;

import com.xiaosong.music.server.domain.Album;
import lombok.Data;

@Data
public class PlayDto {
    private Integer id;
    private String title;
    private String artist;
    private Integer duration;
    private String lyrics;
    private Album album;
    private String url;
    private String image;
    private Boolean islike;
}
