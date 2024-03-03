package com.xiaosong.music.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaosong.music.server.config.JWT.JwtUtils;
import com.xiaosong.music.server.domain.Favorites;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.UserDto;
import com.xiaosong.music.server.error.BizException;
import com.xiaosong.music.server.service.FavoritesService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("favorites")
@Api(value = "收藏夹", tags = "用于音乐库", description = "FavoritesController")
public class FavoritesController {
    @Autowired
    FavoritesService favoritesService;
    @Autowired
    JwtUtils jwtUtils;
    @GetMapping("/get")
    @ApiOperation(value = "分页获取音乐库", notes = "获取音乐库")
    public ResultResponse getFavorites(@RequestParam(required = false) Integer page,@RequestHeader("Authorization") String authHeader){
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();
        if (page<=0 || page==null){
            page=1;
        }
        IPage<Favorites> favorites = favoritesService.getFavorites(page,username);
        return ResultResponse.success(favorites);
    }
    @PostMapping("/set")
    @ApiOperation(value = "放入音乐库", notes = "音乐库")
    public ResultResponse setFavorites(@RequestParam String favType,
                                       @RequestParam Integer favId,
                                       @RequestHeader("Authorization") String authHeader){
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();

        try{
            favoritesService.setFavorites(favType,favId,username);
        }catch (Exception e){
            //插入失败就抛出异常
            throw  new BizException("-1","系统错误，放入音乐库失败");
        }
        return ResultResponse.success("已放入音乐库！");
    }
    @DeleteMapping("/remove")
    @ApiOperation(value = "从音乐库中移除", notes = "音乐库")
    public ResultResponse removeFavorites(@RequestParam String favType,
                                       @RequestParam Integer favId,
                                       @RequestHeader("Authorization") String authHeader){
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();

        try{
            favoritesService.deleteFavorites(favType,favId,username);
        }catch (Exception e){
            //失败就抛出异常
            throw  new BizException("-1","系统错误，从音乐库中移除失败");
        }
        return ResultResponse.success("从音乐库中移除成功！");
    }
}
