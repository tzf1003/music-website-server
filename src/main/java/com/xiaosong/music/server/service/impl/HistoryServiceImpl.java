package com.xiaosong.music.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosong.music.server.domain.Favorites;
import com.xiaosong.music.server.domain.History;
import com.xiaosong.music.server.service.*;
import com.xiaosong.music.server.mapper.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History>
implements HistoryService{
    @Autowired
    HistoryMapper historyMapper;
    @Autowired
    AlbumService albumService;
    @Autowired
    SheetService sheetService;
    @Autowired
    SingerService singerService;
    @Autowired
    MusicService musicService;
    @Override
    public List<History> getHisterys(Integer count) {
        LambdaQueryWrapper<History> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(History::getCreatedAt);
        wrapper.last("limit "+count);
        List<History> history = historyMapper.selectList(wrapper);
        history = setObject(history);
        return history;
    }

    //把相关对象插入到history对象里
    private List<History> setObject(List<History> history){
        for (History record : history) {
            Object obj = null;
            //判断类型处理
            if ("music".equals(record.getHisType())){
                obj = musicService.getById(record.getHisId());
            }else if ("album".equals(record.getHisType())){
                obj = albumService.getById(record.getHisId());
            }else if ("sheet".equals(record.getHisType())){
                obj = sheetService.getById(record.getHisId());
            }else if ("singer".equals(record.getHisType())){
                obj = singerService.getById(record.getHisId());
            }
            record.setHisObj(obj);
        }
        return history;
    }
}




