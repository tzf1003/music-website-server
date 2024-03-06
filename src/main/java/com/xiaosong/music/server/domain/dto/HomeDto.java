package com.xiaosong.music.server.domain.dto;

import com.xiaosong.music.server.domain.*;
import lombok.Data;

import java.util.List;
@Data
public class HomeDto {

    //猜你喜欢
    List<Object> likes;
    //热门歌单
    List<Sheet> hots;
    //热门歌手
    List<Singer> singers;
    //最近播放
    List<History> historys;

}
