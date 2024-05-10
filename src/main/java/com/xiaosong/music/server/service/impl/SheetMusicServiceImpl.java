package com.xiaosong.music.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.domain.SheetMusic;
import com.xiaosong.music.server.service.SheetMusicService;
import com.xiaosong.music.server.mapper.SheetMusicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SheetMusicServiceImpl extends ServiceImpl<SheetMusicMapper, SheetMusic>
implements SheetMusicService{
    @Autowired
    SheetMusicMapper sheetMusicMapper;
    @Override
    public List<Music> selectMusicBySheet(Integer sheetId) {
        return sheetMusicMapper.selectMusicBySheetId(sheetId);
    }

    @Override
    public boolean isLike(Integer sheetId, Integer musicId) {
        // 创建查询条件
        QueryWrapper<SheetMusic> wrapper = new QueryWrapper<>();
        wrapper.eq("sheet", sheetId);  // 设置查询条件
        wrapper.eq("music", musicId);  // 设置查询条件

        // 调用selectCount方法查询符合条件的记录数
        Long count = sheetMusicMapper.selectCount(wrapper);

        // 根据查询结果返回，这里假设如果有记录则返回true，无记录则返回false

        return count > 0;
    }

}




