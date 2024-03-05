package com.xiaosong.music.server.service;

import com.xiaosong.music.server.domain.Sheet;
import com.xiaosong.music.server.domain.Singer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SingerService extends IService<Singer> {
    public List<Singer> SearchSinger(String str);
    public List<Singer> SearchSinger(String str,Long size);
    public List<Singer> SearchSinger(String str,Long page,Long size);

}
