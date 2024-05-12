package com.xiaosong.music.server.controller;

import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.service.MusicService;
import com.xiaosong.music.server.utils.WyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("play")
@Api(value = "播放控制层", tags = "调用播放接口", description = "play")
public class PlayController {
    @Autowired
    MusicService musicService;
    @Autowired
    WyUtil wyUtil;

    @GetMapping("/music")
    @ApiOperation(value = "获取音乐真实url", notes = "获取音乐链接")
    public ResultResponse playMusic(HttpServletResponse response, @RequestParam(required = false) Integer id) throws IOException {
        //以后可能增加鉴权，比如vip音乐只能vip播放
        //调用第三方接口，接口获取音乐
        Music music = musicService.getById(id);
        String musicURLById = null;
        if ("wy".equals(music.getSource())){
            //wyy的url获取

            musicURLById=wyUtil.getMusicURLById(music.getSourceData());
        }else if ("self".equals(music.getSource())){
            //自己添加
            musicURLById=music.getSourceData()+"";
        }
        response.sendRedirect(musicURLById);
        return ResultResponse.success();
    }

}
