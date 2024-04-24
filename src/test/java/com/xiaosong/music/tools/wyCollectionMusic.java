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
//        wyUtil.batchCollectionOfMusicBySongSheet("2009497981");
        wyUtil.batchCollectionOfMusicBySongSheet("2488306802");

        wyUtil.batchCollectionOfMusicBySongSheet("128251515");
        wyUtil.batchCollectionOfMusicBySongSheet("8280551582");
        wyUtil.batchCollectionOfMusicBySongSheet("7737528631");

        wyUtil.batchCollectionOfMusicBySongSheet("8422583693");
        wyUtil.batchCollectionOfMusicBySongSheet("8306564455");
        wyUtil.batchCollectionOfMusicBySongSheet("8413001593");

        wyUtil.batchCollectionOfMusicBySongSheet("8480657327");
        wyUtil.batchCollectionOfMusicBySongSheet("8250466506");
        wyUtil.batchCollectionOfMusicBySongSheet("8901809096");
        wyUtil.batchCollectionOfMusicBySongSheet("130331313");
        wyUtil.batchCollectionOfMusicBySongSheet("8157041911");
        wyUtil.batchCollectionOfMusicBySongSheet("8282413383");
        wyUtil.batchCollectionOfMusicBySongSheet("64945293");
        wyUtil.batchCollectionOfMusicBySongSheet("6891772566");

        wyUtil.batchCollectionOfMusicBySongSheet("2608367080");
        wyUtil.batchCollectionOfMusicBySongSheet("8907116931");
        wyUtil.batchCollectionOfMusicBySongSheet("2954650550");
        wyUtil.batchCollectionOfMusicBySongSheet("2121880263");
        wyUtil.batchCollectionOfMusicBySongSheet("6819880980");
        wyUtil.batchCollectionOfMusicBySongSheet("101354498");
        wyUtil.batchCollectionOfMusicBySongSheet("316223640");
        wyUtil.batchCollectionOfMusicBySongSheet("75182300");
        wyUtil.batchCollectionOfMusicBySongSheet("8811017000");
        wyUtil.batchCollectionOfMusicBySongSheet("8715229144");
        wyUtil.batchCollectionOfMusicBySongSheet("514947114");
        wyUtil.batchCollectionOfMusicBySongSheet("2312165875");
        wyUtil.batchCollectionOfMusicBySongSheet("645384312");
        wyUtil.batchCollectionOfMusicBySongSheet("7725748433");
        wyUtil.batchCollectionOfMusicBySongSheet("8070816063");
        wyUtil.batchCollectionOfMusicBySongSheet("637718317");
    }
}
