package com.xiaosong.music.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosong.music.server.domain.Album;
import com.xiaosong.music.server.domain.Singer;
import com.xiaosong.music.server.service.SingerService;
import com.xiaosong.music.server.mapper.SingerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 *
 */
@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer>
implements SingerService{
    @Autowired
    SingerMapper singerMapper;
    @Override
    public List<Singer> SearchSinger(String str) {
        //创建分页对象,只获取五个
        Page<Singer> page = new Page<>(0, 5);
        LambdaQueryWrapper<Singer> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Singer::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Singer> categoryPageResult = singerMapper.selectPage(page, wrapper);

        //获得分页结果中的所有数据
        List<Singer> records = categoryPageResult.getRecords();
        return records;
    }

    @Override
    public List<Singer> SearchSinger(String str, Long size) {
        //创建分页对象,只获取五个
        Page<Singer> page = new Page<>(0, size);
        LambdaQueryWrapper<Singer> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Singer::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Singer> categoryPageResult = singerMapper.selectPage(page, wrapper);

        //获得分页结果中的所有数据
        List<Singer> records = categoryPageResult.getRecords();
        return records;
    }

    @Override
    public List<Singer> SearchSinger(String str, Long page, Long size) {
        if (page<1){
            page= 1L;
        }
        //创建分页对象
        Page<Singer> pageObj = new Page<>(page-1, size);
        LambdaQueryWrapper<Singer> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Singer::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Singer> categoryPageResult = singerMapper.selectPage(pageObj, wrapper);
        //获得分页结果中的所有数据
        List<Singer> records = categoryPageResult.getRecords();
        return records;
    }

    @Override
    public Singer RandomSinger() {
        Random random = new Random();
        int count = Math.toIntExact(singerMapper.selectCount(null));
        int offset = random.nextInt(count);
        Wrapper<Singer> wrapper = new QueryWrapper<Singer>().last("limit 1 offset "+offset);
        Singer singer = singerMapper.selectOne(wrapper);
        return singer;
    }
}




