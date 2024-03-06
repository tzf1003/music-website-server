package com.xiaosong.music.tools;

import com.xiaosong.music.server.ServerApplication;
import com.xiaosong.music.server.utils.WyUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//    wy批量采集音乐
@SpringBootTest(classes = {ServerApplication.class})
public class wyCollectionMusic {
    @Autowired
    WyUtil wyUtil;

    //通过歌单 批量采集音乐
    @Test
    public void batchCollectionOfMusicBySongSheet(){
        wyUtil.batchCollectionOfMusicBySongSheet("98317854");
    }
}
