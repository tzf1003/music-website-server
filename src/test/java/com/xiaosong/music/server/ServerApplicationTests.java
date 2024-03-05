package com.xiaosong.music.server;

import com.xiaosong.music.server.domain.Music;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.xiaosong.music.server.service.MusicService;

import java.util.List;

@SpringBootTest
class ServerApplicationTests {
    @Autowired
    MusicService musicService;
    @Test
    void contextLoads() {
        List<Music> music = musicService.SearchMusic("1");
        System.out.println(music);
    }

}
