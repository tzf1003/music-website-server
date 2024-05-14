package com.xiaosong.music.server.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaosong.music.server.domain.Singer;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.service.SingerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("admin/singer")
@Api(value = "歌手管理", tags = "管理员相关的接口", description = "SingerAdmin")
public class AdminSingerController {
    @Autowired
    SingerService singerService;


    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员搜索歌手", notes = "search")
    public ResultResponse adminSearch(@RequestHeader("Authorization") String authHeader,String str,Long page, Long size) {
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
        Page<Singer> pages = new Page<>(page, size);
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", str); // 按照name进行搜索
        pages = singerService.page(pages, queryWrapper);
        return ResultResponse.success(pages);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员获取歌手", notes = "get")
    public ResultResponse get(@RequestHeader("Authorization") String authHeader,@PathVariable Integer id) {
        return ResultResponse.success(singerService.getById(id));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员新增歌手", notes = "post")
    public ResultResponse post(@RequestHeader("Authorization") String authHeader,@RequestBody Singer singer) {
        boolean save = singerService.save(singer);
        if (save){
            return ResultResponse.success("","新增成功！");
        }else {
            return ResultResponse.error("新增失败！");
        }

    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员更新歌手", notes = "Put")
    public ResultResponse put(@RequestHeader("Authorization") String authHeader,@PathVariable Integer id,@RequestBody Singer singer) {
        boolean b = singerService.updateById(singer);
        if (b){
            return ResultResponse.success("","更新成功！");
        }else {
            return ResultResponse.error("更新失败！");
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员删除歌手", notes = "Delete")
    public ResultResponse delete(@RequestHeader("Authorization") String authHeader,@PathVariable Integer id) {
        boolean b = singerService.removeById(id);
        if (b){
            return ResultResponse.success("","删除成功！");
        }else {
            return ResultResponse.error("删除失败！");
        }
    }
}
