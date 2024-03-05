package com.xiaosong.music.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.domain.Sheet;
import com.xiaosong.music.server.service.SheetService;
import com.xiaosong.music.server.mapper.SheetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SheetServiceImpl extends ServiceImpl<SheetMapper, Sheet>
implements SheetService{
    @Autowired
    SheetMapper sheetMapper;
    @Override
    public List<Sheet> SearchSheet(String str) {
        //创建分页对象,只获取五个
        Page<Sheet> page = new Page<>(0, 5);
        LambdaQueryWrapper<Sheet> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Sheet::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Sheet> categoryPageResult = sheetMapper.selectPage(page, wrapper);

        //获得分页结果中的所有数据
        List<Sheet> records = categoryPageResult.getRecords();
        return records;
    }

    @Override
    public List<Sheet> SearchSheet(String str, Long size) {
        //创建分页对象,只获取五个
        Page<Sheet> page = new Page<>(0, size);
        LambdaQueryWrapper<Sheet> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Sheet::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Sheet> categoryPageResult = sheetMapper.selectPage(page, wrapper);

        //获得分页结果中的所有数据
        List<Sheet> records = categoryPageResult.getRecords();
        return records;
    }

    @Override
    public List<Sheet> SearchSheet(String str, Long page, Long size) {
        if (page<1){
            page= 1L;
        }
        //创建分页对象,只获取五个
        Page<Sheet> pageObj = new Page<>(page-1, size);
        LambdaQueryWrapper<Sheet> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Sheet::getName, str);

        //MybatisPlus实现分页结果搜索查询
        Page<Sheet> categoryPageResult = sheetMapper.selectPage(pageObj, wrapper);

        //获得分页结果中的所有数据
        List<Sheet> records = categoryPageResult.getRecords();
        return records;
    }
}




