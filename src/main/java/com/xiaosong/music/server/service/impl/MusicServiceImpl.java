package com.xiaosong.music.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.service.MusicService;
import com.xiaosong.music.server.mapper.MusicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music>
implements MusicService{
    @Autowired
    MusicMapper musicMapper;

    @Override
    public List<Music> SearchMusic(String str) {
        //创建分页对象,只获取五个
        Page<Music> page = new Page<>(0, 5);
        LambdaQueryWrapper<Music> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Music::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Music> categoryPageResult = musicMapper.selectPage(page, wrapper);

        //获得分页结果中的所有数据
        List<Music> records = categoryPageResult.getRecords();
        return records;
    }

    public List<Music> SearchMusic(String str,Long size) {
        //创建分页对象
        Page<Music> page = new Page<>(0, size);
        LambdaQueryWrapper<Music> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Music::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Music> categoryPageResult = musicMapper.selectPage(page, wrapper);

        //获得分页结果中的所有数据
        List<Music> records = categoryPageResult.getRecords();
        return records;
    }
    public List<Music> SearchMusic(String str,Long page,Long size) {
        if (page<1){
            page= 1L;
        }
        //创建分页对象
        Page<Music> pageObj = new Page<>(page-1, size);
        LambdaQueryWrapper<Music> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Music::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Music> categoryPageResult = musicMapper.selectPage(pageObj, wrapper);
        //获得分页结果中的所有数据
        List<Music> records = categoryPageResult.getRecords();
        return records;
    }
}




