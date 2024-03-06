package com.xiaosong.music.server.controller;

import com.xiaosong.music.server.domain.History;
import com.xiaosong.music.server.domain.Sheet;
import com.xiaosong.music.server.domain.Singer;
import com.xiaosong.music.server.domain.dto.HomeDto;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.error.BizException;

import com.xiaosong.music.server.service.HistoryService;
import com.xiaosong.music.server.service.MusicService;
import com.xiaosong.music.server.service.SheetService;
import com.xiaosong.music.server.service.SingerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "测试接口", tags = "用户管理相关的接口", description = "用户测试接口")
public class IndexController {
    @Autowired
    SheetService sheetService;
    @Autowired
    SingerService singerService;
    @Autowired
    MusicService musicService;
    @Autowired
    HistoryService historyService;
    @GetMapping("/hello")
//    @PreAuthorize("hasAuthority('NORMAL_ACCOUNT')")
    @ApiOperation(value = "添加用户", notes = "添加用户")
    @ApiImplicitParam(name = "name", value = "名称")
    public ResultResponse index(@RequestParam(name = "name",required = false) String name) {
        //如果name为空就手动抛出一个自定义的异常！
        if(name==null){
            throw  new BizException("-1","name不能为空！");
        }
        return ResultResponse.success("hello " + name);
    }

    @GetMapping("/home")
    @ApiOperation(value = "获取主界面的展示的信息", notes = "根据用户情况")
    @ApiImplicitParam(name = "authHeader", value = "token")
    public ResultResponse home( @RequestHeader("Authorization") String authHeader) {
        //如果name为空就手动抛出一个自定义的异常！
        HomeDto homeDto = new HomeDto();
        //获取猜你喜欢，目前先做随机读取。
        List<Object> likes= new ArrayList<>();
        //两个随机歌单
        likes.add(sheetService.RandomSheet());
        likes.add(sheetService.RandomSheet());
        //两个随机歌手
        likes.add(singerService.RandomSinger());
        likes.add(singerService.RandomSinger());
        //两个随机歌曲
        likes.add(musicService.RandomMusic());
        likes.add(musicService.RandomMusic());
        homeDto.setLikes(likes);

        //热门歌单
        List<Sheet> hots= new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            hots.add(sheetService.RandomSheet());
        }
        homeDto.setHots(hots);
        //热门歌手
        List<Singer> singers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            singers.add(singerService.RandomSinger());
        }
        homeDto.setSingers(singers);

        //历史记录
        List<History> histerys = historyService.getHisterys(10);
        homeDto.setHistorys(histerys);
        return ResultResponse.success(homeDto);
    }
}