package com.xiaosong.music.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosong.music.server.domain.Album;
import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.domain.Sheet;
import com.xiaosong.music.server.service.AlbumService;
import com.xiaosong.music.server.mapper.AlbumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album>
implements AlbumService{
    @Autowired
    AlbumMapper albumMapper;

    @Override
    public List<Album> SearchAlbum(String str) {
        //创建分页对象,只获取五个
        Page<Album> page = new Page<>(0, 10);
        LambdaQueryWrapper<Album> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Album::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Album> categoryPageResult = albumMapper.selectPage(page, wrapper);

        //获得分页结果中的所有数据
        List<Album> records = categoryPageResult.getRecords();
        return records;
    }

    @Override
    public List<Album> SearchAlbum(String str, Long size) {
        //创建分页对象,只获取五个
        Page<Album> page = new Page<>(0, size);
        LambdaQueryWrapper<Album> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Album::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Album> categoryPageResult = albumMapper.selectPage(page, wrapper);

        //获得分页结果中的所有数据
        List<Album> records = categoryPageResult.getRecords();
        return records;
    }

    @Override
    public List<Album> SearchAlbum(String str, Long page, Long size) {
        if (page<1){
            page= 1L;
        }
        //创建分页对象
        Page<Album> pageObj = new Page<>(page-1, size);
        LambdaQueryWrapper<Album> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Album::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Album> categoryPageResult = albumMapper.selectPage(pageObj, wrapper);
        //获得分页结果中的所有数据
        List<Album> records = categoryPageResult.getRecords();
        return records;
    }
}




