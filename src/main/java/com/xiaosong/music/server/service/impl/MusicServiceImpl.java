package com.xiaosong.music.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.domain.Sheet;
import com.xiaosong.music.server.domain.User;
import com.xiaosong.music.server.service.MusicService;
import com.xiaosong.music.server.mapper.MusicMapper;
import com.xiaosong.music.server.service.SheetMusicService;
import com.xiaosong.music.server.service.SheetService;
import com.xiaosong.music.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 *
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music>
implements MusicService{
    @Autowired
    MusicMapper musicMapper;
    @Autowired
    UserService userService;
    @Autowired
    SheetMusicService sheetMusicService;
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

    @Override
    public Music RandomMusic() {
        Random random = new Random();
        int count = Math.toIntExact(musicMapper.selectCount(null));
        int offset = random.nextInt(count);
        Wrapper<Music> wrapper = new QueryWrapper<Music>().last("limit 1 offset "+offset);
        Music music = musicMapper.selectOne(wrapper);
        return music;
    }

    @Override
    public Boolean isLike(String username, Integer musicId) {
        User user =userService.selectUserByUsername(username);
        Integer userLiked = user.getLiked();
        return sheetMusicService.isLike(userLiked, musicId);
    }

}




