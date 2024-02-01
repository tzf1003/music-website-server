package com.xiaosong.music.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName xs_singer_music
 */
@TableName(value ="xs_singer_music")
@Data
public class SingerMusic implements Serializable {
    /**
     * 自增ID
     */
    @TableId
    private Integer id;

    /**
     * 歌手
     */
    private Integer singer;

    /**
     * 音乐
     */
    private Integer music;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SingerMusic other = (SingerMusic) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSinger() == null ? other.getSinger() == null : this.getSinger().equals(other.getSinger()))
            && (this.getMusic() == null ? other.getMusic() == null : this.getMusic().equals(other.getMusic()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSinger() == null) ? 0 : getSinger().hashCode());
        result = prime * result + ((getMusic() == null) ? 0 : getMusic().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", singer=").append(singer);
        sb.append(", music=").append(music);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}