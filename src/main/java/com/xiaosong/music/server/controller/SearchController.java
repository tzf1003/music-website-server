package com.xiaosong.music.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaosong.music.server.domain.*;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.SearchDto;
import com.xiaosong.music.server.service.AlbumService;
import com.xiaosong.music.server.service.MusicService;
import com.xiaosong.music.server.service.SheetService;
import com.xiaosong.music.server.service.SingerService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    SingerService singerService;
    @GetMapping("/all")
    @ApiOperation(value = "默认搜索所有相关信息", notes = "搜索任何内容")
    public ResultResponse searchAll(@RequestParam String  str, @RequestHeader("Authorization") String authHeader) {
        //设置输出的对象
        SearchDto searchDto = new SearchDto();
        //开始查询音乐
        List<Music> musics = musicService.SearchMusic(str);
        searchDto.setMusics(musics);
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
