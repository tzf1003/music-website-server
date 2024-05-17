package com.xiaosong.music.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaosong.music.server.config.JWT.JwtUtils;
import com.xiaosong.music.server.domain.*;
import com.xiaosong.music.server.domain.dto.MusicDto;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.SearchDto;
import com.xiaosong.music.server.service.*;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
@Api(value = "搜索的控制层", tags = "搜索", description = "SearchController")
public class SearchController {
    @Autowired
    MusicService musicService;
    @Autowired
    SheetService sheetService;
    @Autowired
    AlbumService albumService;
    @Autowired
    AlbumMusicService albumMusicService;
    @Autowired
    SingerMusicService singerMusicService;
    @Autowired
    SheetMusicService sheetMusicService;
    @Autowired
    SingerService singerService;
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @GetMapping("/all")
    @ApiOperation(value = "默认搜索所有相关信息", notes = "搜索任何内容")
    public ResultResponse searchAll(@RequestParam String  str, @RequestHeader("Authorization") String authHeader) {
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();
        //设置输出的对象
        SearchDto searchDto = new SearchDto();
        //开始查询音乐
        List<Music> musics = musicService.SearchMusic(str);
        User sheetUser = userService.selectUserByUsername(username);
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
        searchDto.setMusics(musicDto);
        //开始查询歌单
        List<Sheet> sheets =  sheetService.SearchSheet(str);
        searchDto.setSheets(sheets);
        //开始查询专辑
        List<Album> albums = albumService.SearchAlbum(str);
        searchDto.setAlbums(albums);
        //开始查询歌手
        List<Singer> singers =  singerService.SearchSinger(str);
        searchDto.setSingers(singers);
        return ResultResponse.success(searchDto);
    }
}
