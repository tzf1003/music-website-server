package com.xiaosong.music.server.service;

import com.xiaosong.music.server.domain.History;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface HistoryService extends IService<History> {

    public List<History> getHisterys(Integer count);
}
