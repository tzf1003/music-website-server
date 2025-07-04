package com.xiaosong.music.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaosong.music.server.config.JWT.JwtUtils;
import com.xiaosong.music.server.domain.*;
import com.xiaosong.music.server.domain.dto.MusicDto;
import com.xiaosong.music.server.domain.dto.PlayDto;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.SheetDto;
import com.xiaosong.music.server.error.BizException;
import com.xiaosong.music.server.service.*;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sheet")
@Api(value = "歌单接口", tags = "歌单管理相关的接口", description = "歌单接口")
public class SheetController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    SheetService sheetService;
    @Autowired
    UserService userService;
    @Autowired
    SheetMusicService sheetMusicService;
    @Autowired
    AlbumMusicService albumMusicService;
    @Autowired
    SingerMusicService singerMusicService;
    @Autowired
    MusicService musicService;
    @Autowired
    HistoryService historyService;
    @GetMapping("/get")
    @ApiOperation(value = "分页获取音乐库", notes = "获取音乐库")
    public ResultResponse getSheet(@RequestParam(required = false) Integer id, @RequestHeader("Authorization") String authHeader){
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();
        //获取歌单信息
        Sheet sheet = sheetService.getById(id);
        //判断是否有权限
        // 判断是否公开,0不公开，1公开
        if (sheet.getIsPublic()==0){
            if (userService.selectUserByUsername(username).getId()!=sheet.getUser()){
                //无权限抛出异常
                throw new BizException("-1", "没有权限！");
            }
        }
        User sheetUser = userService.getById(sheet.getUser());

        SheetDto sheetDto = new SheetDto();

        sheetDto.setId(sheet.getId());
        sheetDto.setName(sheet.getName());
        sheetDto.setDescription(sheet.getDescription());
        sheetDto.setImgUrl(sheet.getImgUrl());
        sheetDto.setUsername(sheetUser.getUsername());
        sheetDto.setIsPublic(sheet.getIsPublic());
        List<Music> musics = sheetMusicService.selectMusicBySheet(sheet.getId());
        List<MusicDto> musicDto = new ArrayList<>();
        for (Music music : musics) {
            MusicDto dto = new MusicDto();
            dto.setMusic(music);  // 设置Music对象
            musicDto.add(dto);
            List<Album> albums = albumMusicService.selectAlbumByMusicId(music.getId());
            dto.setAlbumObject(albums.get(0));
            List<Singer> singers = singerMusicService.selectSingerByMusicId(music.getId());
            dto.setSingerObject(singers);
            boolean isLike = sheetMusicService.isLike(sheetUser.getLiked(), music.getId());
            dto.setIsLike(isLike);
        }
        sheetDto.setMusicList(musicDto);
        sheetDto.setCount(musicDto.size());
        return ResultResponse.success(sheetDto);
    }
    @GetMapping("/play")
    @ApiOperation(value = "播放音乐", notes = "获取音乐")
    public ResultResponse sheetPlay(@RequestParam(required = false) Integer id, @RequestHeader("Authorization") String authHeader){
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();
        //获取歌单信息
        Sheet sheet = sheetService.getById(id);
        //判断是否有权限
        // 判断是否公开,0不公开，1公开
        User user = userService.selectUserByUsername(username);
        if (sheet.getIsPublic()==0){
            if (user.getId()!=sheet.getUser()){
                //无权限抛出异常
                throw new BizException("-1", "没有权限！");
            }
        }
        //列出所有歌曲id
        List<Music> musics = sheetMusicService.selectMusicBySheet(id);
        List<PlayDto> playDtos= new ArrayList<>();
        for (Music music : musics) {
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
        }
        //记录历史播放
        History history = new History();
        history.setUserId(user.getId());
        history.setHisType("sheet");
        history.setHisId(id);
        try {
            //先查询出数据，没有就新建，有就修改时间为现在
            LambdaQueryWrapper<History> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(History::getUserId,user.getId()).eq(History::getHisId,id);
            History his = historyService.getOne(queryWrapper);
            if (his!=null){
                historyService.removeById(his);
            }
            historyService.save(history);
        }catch (DuplicateKeyException e){
            //多次插入相同的历史数据会报错
        }

        return ResultResponse.success(playDtos);
    }
}
