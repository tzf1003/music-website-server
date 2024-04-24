package com.xiaosong.music.server.domain;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.xiaosong.music.server.service.AlbumMusicService;
import com.xiaosong.music.server.service.AlbumService;
import com.xiaosong.music.server.service.SingerMusicService;
import com.xiaosong.music.server.service.impl.AlbumMusicServiceImpl;
import com.xiaosong.music.server.service.impl.SingerMusicServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @TableName xs_music
 */
@TableName(value ="xs_music")
@Data
public class Music implements Serializable {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 歌曲名
     */
    private String name;

    /**
     * 歌词
     */
    private String lyric;

    /**
     * 来源，此歌曲的来源，比如链接，还是其他网站的ID
     */
    private String source;

    /**
     * 来源数据，里面的内容和source相关。
     */
    private String sourceData;

    /**
     * 图片
     */
    private String imgUrl;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建时间
     */
    private Integer duration;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

