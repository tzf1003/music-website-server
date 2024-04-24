package com.xiaosong.music.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaosong.music.server.domain.Favorites;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaosong.music.server.domain.User;

import java.util.List;

/**
 *
 */
public interface FavoritesService extends IService<Favorites> {
    public IPage<Favorites> getFavorites(Integer page , String username);
    public void setFavorites(String favType,Integer favId ,String username);
    public void deleteFavorites(String favType,Integer favId ,String username);

}
