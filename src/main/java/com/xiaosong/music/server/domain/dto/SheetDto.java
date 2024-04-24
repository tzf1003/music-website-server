package com.xiaosong.music.server.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xiaosong.music.server.domain.Music;
import lombok.Data;

import java.util.List;

@Data
public class SheetDto {
    /**
     * 自增ID
     */
    private Integer id;
    /**
     * 歌单名称
     */
    private String name;
    /**
     * 歌单详情(描述)
     */
    private String description;
    /**
     * 创建者名字
     */
    private String username;

    /**
     * 图片url
     */
    private String imgUrl;

    /**
     * 是否公开
     */
    private Integer isPublic;
    /**
     * 歌曲数量
     */
    private Integer count;
    /**
     * 音乐列表
     */
    private List<MusicDto> musicList;
}
