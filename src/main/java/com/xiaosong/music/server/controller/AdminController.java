package com.xiaosong.music.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaosong.music.server.domain.AlbumMusic;
import com.xiaosong.music.server.domain.Music;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.service.AlbumService;
import com.xiaosong.music.server.service.MusicService;
import com.xiaosong.music.server.service.SheetService;
import com.xiaosong.music.server.service.SingerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("admin")
@Api(value = "管理员", tags = "管理员相关的接口", description = "Captcha")
public class AdminController {
    @Autowired
    MusicService musicService;
    @Autowired
    SheetService sheetService;
    @Autowired
    AlbumService albumService;
    @Autowired
    SingerService singerService;

    @GetMapping("/home")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员主页", notes = "展示系统信息")
    public ResultResponse adminHome(@RequestHeader("Authorization") String authHeader) {
        //获取总播放量
        //获取歌曲数量
        //获取歌单数量
        //获取用户数量

        //获取系统版本
        //获取服务器信息
        //获取数据库信息
        //获取服务器负载
        //获取数据库负载
        //获取服务器内存

        return ResultResponse.success();
    }

    @GetMapping("/music/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员搜索歌曲", notes = "展示歌曲信息")
    public ResultResponse adminSearchMusic(@RequestHeader("Authorization") String authHeader,String str,Long page, Long size) {
        //默认
        if (page==null || page<=0){
            page = 10L;
        }
        if (size==null || size<=0){
            size = 10L;
        }
        if (str==null){
            str = "";
        }
        Page<Music> musicPages = new Page<>(page, size);
        QueryWrapper<Music> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", str); // 按照音乐名进行搜索
        musicPages = musicService.page(musicPages, queryWrapper);
        return ResultResponse.success(musicPages);
    }

    @GetMapping("/music/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员获取歌曲", notes = "get")
    public ResultResponse getMusic(@RequestHeader("Authorization") String authHeader,@PathVariable Integer id) {
        return ResultResponse.success(musicService.getById(id));
    }

    @PostMapping("/music/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员新增歌曲", notes = "post")
    public ResultResponse postMusic(@RequestHeader("Authorization") String authHeader,@PathVariable Integer id,Music music) {
        boolean save = musicService.save(music);
        if (save){
            return ResultResponse.success("新增成功！");
        }else {
            return ResultResponse.error("新增失败！");
        }

    }
    @PutMapping("/music/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员更新歌曲", notes = "Put")
    public ResultResponse putMusic(@RequestHeader("Authorization") String authHeader,@PathVariable Integer id,Music music) {
        boolean b = musicService.updateById(music);
        if (b){
            return ResultResponse.success("更新成功！");
        }else {
            return ResultResponse.error("更新失败！");
        }
    }
    @DeleteMapping("/music/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员删除歌曲", notes = "Delete")
    public ResultResponse deleteMusic(@RequestHeader("Authorization") String authHeader,@PathVariable Integer id) {
        boolean b = musicService.removeById(id);
        if (b){
            return ResultResponse.success("删除成功！");
        }else {
            return ResultResponse.error("删除失败！");
        }
    }
}
