package com.xiaosong.music.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosong.music.server.domain.Favorites;
import com.xiaosong.music.server.domain.User;
import com.xiaosong.music.server.service.*;
import com.xiaosong.music.server.mapper.FavoritesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites>
implements FavoritesService{
    @Autowired
    FavoritesMapper favoritesMapper;
    @Autowired
    UserService userService;
    @Autowired
    AlbumService albumService;
    @Autowired
    SheetService sheetService;
    @Autowired
    SingerService singerService;
    @Override
    public IPage<Favorites> getFavorites(Integer page, String username) {
        //获取到用户
        User user = userService.selectUserByUsername(username);
        //设置查询条件map
        Map<String, Object> favoritesParams = new HashMap<>();
        favoritesParams.put("user_id", user.getId());
        //设置查询方式
        QueryWrapper favoritesQueryWrapper= new QueryWrapper();
        favoritesQueryWrapper.allEq(favoritesParams);
        //设置分页查询。
        Page<Favorites> samplePage = new Page<>(page, 1000);
        IPage<Favorites> result = favoritesMapper.selectPage(samplePage, favoritesQueryWrapper);
        //循环出数据
        List<Favorites> records = result.getRecords();
        //插入相应数据。
        result.setRecords(setObject(records));
        return result;
    }

    @Override
    public void setFavorites(String favType, Integer favId, String username) {
        //获取到用户
        User user = userService.selectUserByUsername(username);
        //设置值
        Favorites favorites = new Favorites();
        favorites.setFavType(favType);
        favorites.setFavId(favId);
        favorites.setUserId(user.getId());
        //插入到数据库
        favoritesMapper.insert(favorites);
    }

    @Override
    public void deleteFavorites(String favType, Integer favId, String username) {
        //获取到用户
        User user = userService.selectUserByUsername(username);
        //设置查询条件map
        Map<String, Object> favoritesParams = new HashMap<>();
        favoritesParams.put("fav_type", favType);
        favoritesParams.put("fav_id", favId);
        favoritesParams.put("user_id", user.getId());

        favoritesMapper.deleteByMap(favoritesParams);
    }

    //把相关对象插入到Favorites对象里
    private List<Favorites> setObject(List<Favorites> records){
        for (Favorites record : records) {
            Object obj = null;
            //判断类型处理
            if ("album".equals(record.getFavType())){
                obj = albumService.getById(record.getFavId());
            }else if ("sheet".equals(record.getFavType())){
                obj = sheetService.getById(record.getFavId());
            }else if ("singer".equals(record.getFavType())){
                obj = singerService.getById(record.getFavId());
            }
            record.setFavObj(obj);
        }
        return records;
    }
}




