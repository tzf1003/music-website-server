package com.xiaosong.music.server.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class SingerDto implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;


    private String name;


    private String description;


    private String imgUrl;

    private String img1v1Url;

    /**
     * musicList
     */
    private List<MusicDto> musicList;

    /**
     * 歌曲数量
     */
    private Integer count;

}