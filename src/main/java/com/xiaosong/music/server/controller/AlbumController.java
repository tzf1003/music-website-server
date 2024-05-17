package com.xiaosong.music.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaosong.music.server.config.JWT.JwtUtils;
import com.xiaosong.music.server.domain.*;
import com.xiaosong.music.server.domain.dto.MusicDto;
import com.xiaosong.music.server.domain.dto.PlayDto;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.AlbumDto;
import com.xiaosong.music.server.error.BizException;
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
@RequestMapping("/album")
@Api(value = "专辑接口", tags = "专辑管理相关的接口", description = "专辑接口")
public class AlbumController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AlbumService albumService;
    @Autowired
    UserService userService;
    @Autowired
    AlbumMusicService albumMusicService;
    @Autowired
    SheetMusicService sheetMusicService;
    @Autowired
    SingerMusicService singerMusicService;
    @Autowired
    MusicService musicService;
    @Autowired
    HistoryService historyService;
    @GetMapping("/get")
    @ApiOperation(value = "分页获取专辑", notes = "获取专辑")
    public ResultResponse getAlbum(@RequestParam(required = false) Integer id, @RequestHeader("Authorization") String authHeader){
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();
        //读取用户信息
        User user = userService.selectUserByUsername(username);
        //获取专辑信息
        Album album = albumService.getById(id);
        AlbumDto albumDto = new AlbumDto();

        albumDto.setId(album.getId());
        albumDto.setName(album.getName());
        albumDto.setImgUrl(album.getImgUrl());
        List<Music> musics = albumMusicService.selectMusicByAlbum(album.getId());
        List<MusicDto> musicDto = new ArrayList<>();
        for (Music music : musics) {
            MusicDto dto = new MusicDto();
            dto.setMusic(music);  // 设置Music对象
            musicDto.add(dto);
            List<Album> albums = albumMusicService.selectAlbumByMusicId(music.getId());
            dto.setAlbumObject(albums.get(0));
            List<Singer> singers = singerMusicService.selectSingerByMusicId(music.getId());
            dto.setSingerObject(singers);
            boolean isLike = sheetMusicService.isLike(user.getLiked(), music.getId());
            dto.setIsLike(isLike);
        }
        albumDto.setMusicList(musicDto);
        albumDto.setCount(musicDto.size());
        return ResultResponse.success(albumDto);
    }
    @GetMapping("/play")
    @ApiOperation(value = "播放音乐", notes = "获取音乐")
    public ResultResponse albumPlay(@RequestParam(required = false) Integer id, @RequestHeader("Authorization") String authHeader){
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();
        User user = userService.selectUserByUsername(username);
        //获取专辑信息
        Album album = albumService.getById(id);
        //列出所有歌曲id
        List<Music> musics = albumMusicService.selectMusicByAlbum(id);
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
        history.setHisType("album");
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
