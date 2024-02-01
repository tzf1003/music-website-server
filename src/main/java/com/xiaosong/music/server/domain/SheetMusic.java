package com.xiaosong.music.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 歌单和歌曲关联表
 * @TableName xs_sheet_music
 */
@TableName(value ="xs_sheet_music")
@Data
public class SheetMusic implements Serializable {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 歌单ID
     */
    private Integer sheet;

    /**
     * 歌单所关联的歌曲。
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
        SheetMusic other = (SheetMusic) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSheet() == null ? other.getSheet() == null : this.getSheet().equals(other.getSheet()))
            && (this.getMusic() == null ? other.getMusic() == null : this.getMusic().equals(other.getMusic()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSheet() == null) ? 0 : getSheet().hashCode());
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
        sb.append(", sheet=").append(sheet);
        sb.append(", music=").append(music);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}