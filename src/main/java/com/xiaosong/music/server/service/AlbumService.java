package com.xiaosong.music.server.service;

import com.xiaosong.music.server.domain.Album;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaosong.music.server.domain.Sheet;

import java.util.List;

/**
 *
 */
public interface AlbumService extends IService<Album> {
    public List<Album> SearchAlbum(String str);
    public List<Album> SearchAlbum(String str,Long size);
    public List<Album> SearchAlbum(String str,Long page,Long size);

}
