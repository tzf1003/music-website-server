package com.xiaosong.music.server.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaosong.music.server.domain.Music;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AlbumDto implements Serializable {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 专辑名称
     */
    private String name;

    /**
     * 专辑图片url
     */
    private String imgUrl;

    /**
     * 来源
     */
    private String source;

    /**
     * 来源id
     */
    private String sourceId;

    /**
     * musicList
     */
    private List<MusicDto> musicList;

    /**
     * 歌曲数量
     */
    private Integer count;

}