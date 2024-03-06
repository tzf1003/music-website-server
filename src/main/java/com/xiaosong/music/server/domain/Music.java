package com.xiaosong.music.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

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
     * 专辑ID
     */
    private Integer album;

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
        Music other = (Music) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAlbum() == null ? other.getAlbum() == null : this.getAlbum().equals(other.getAlbum()))
            && (this.getLyric() == null ? other.getLyric() == null : this.getLyric().equals(other.getLyric()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getSourceData() == null ? other.getSourceData() == null : this.getSourceData().equals(other.getSourceData()))
            && (this.getImgUrl() == null ? other.getImgUrl() == null : this.getImgUrl().equals(other.getImgUrl()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAlbum() == null) ? 0 : getAlbum().hashCode());
        result = prime * result + ((getLyric() == null) ? 0 : getLyric().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getSourceData() == null) ? 0 : getSourceData().hashCode());
        result = prime * result + ((getImgUrl() == null) ? 0 : getImgUrl().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", album=").append(album);
        sb.append(", lyric=").append(lyric);
        sb.append(", source=").append(source);
        sb.append(", sourceData=").append(sourceData);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}