package com.xiaosong.music.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaosong.music.server.config.JWT.JwtUtils;
import com.xiaosong.music.server.domain.*;
import com.xiaosong.music.server.domain.dto.MusicDto;
import com.xiaosong.music.server.domain.dto.PlayDto;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.MusicDto;
import com.xiaosong.music.server.service.*;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/music")
@Api(value = "歌曲接口", tags = "歌曲管理相关的接口", description = "歌曲接口")
public class MusicController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    MusicService musicService;
    @Autowired
    UserService userService;
    @Autowired
    AlbumMusicService albumMusicService;
    @Autowired
    SheetMusicService sheetMusicService;
    @Autowired
    HistoryService historyService;
    @Autowired
    SingerMusicService singerMusicService;
    @GetMapping("/get")
    @ApiOperation(value = "获取歌曲", notes = "获取歌曲")
    public ResultResponse getMusic(@RequestParam(required = false) Integer id, @RequestHeader("Authorization") String authHeader){
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();
        //读取用户信息
        User user = userService.selectUserByUsername(username);
        //获取歌曲信息
        Music music = musicService.getById(id);
        MusicDto musicDto = new MusicDto();

        musicDto.setMusic(music);
        List<Singer> singers = singerMusicService.selectSingerByMusicId(music.getId());
        musicDto.setSingerObject(singers);
        List<Album> albums = albumMusicService.selectAlbumByMusicId(music.getId());
        musicDto.setAlbumObject(albums.get(0));
        boolean like = musicService.isLike(username, music.getId());
        musicDto.setIsLike(like);
        return ResultResponse.success(musicDto);
    }
    @GetMapping("/play")
    @ApiOperation(value = "播放音乐", notes = "获取音乐")
    public ResultResponse musicPlay(@RequestParam(required = false) Integer id, @RequestHeader("Authorization") String authHeader){
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();
        User user = userService.selectUserByUsername(username);
        //获取歌曲信息
        Music music = musicService.getById(id);

        List<PlayDto> playDtos= new ArrayList<>();
        PlayDto playDto = new PlayDto();
        playDto.setId(music.getId());
        playDto.setTitle(music.getName());
        playDto.setArtist(singerMusicService.getSingerStrByMusic(music.getId()));
        playDto.setDuration(music.getDuration());
        playDto.setLyrics(music.getLyric());
        playDto.setAlbum(albumMusicService.selectAlbumByMusicId(music.getId()).get(0));
        playDto.setUrl("/api/play/music?id="+music.getId());
        playDto.setImage(music.getImgUrl());
        playDto.setIslike(musicService.isLike(username,music.getId()));
        playDtos.add(playDto);
        return ResultResponse.success(playDtos);
    }
}
