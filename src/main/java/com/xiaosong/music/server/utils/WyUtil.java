package com.xiaosong.music.server.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaosong.music.server.domain.*;
import com.xiaosong.music.server.service.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WyUtil {
    @Autowired
    MusicService musicService;
    @Autowired
    SheetService sheetService;
    @Autowired
    SingerService singerService;
    @Autowired
    AlbumService albumService;
    @Autowired
    SingerMusicService singerMusicService;
    @Autowired
    AlbumMusicService albumMusicService;
    @Autowired
    SheetMusicService sheetMusicService;
    @Autowired
    AlbumSingerService albumSingerService;
    private Logger logger = LoggerFactory.getLogger(WyUtil.class);
    @Value("${music.api.wy-base-url}")
    private String baseUrl;

    //使用音乐ID获取音乐链接
    public String getMusicURLById(Integer musicId){
        logger.info("getMusicURLById:{}",musicId);
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "/lyric?id=" + musicId ;
        try {
            //获取歌曲链接 http://101.42.149.18:3000/song/url?id=33894312
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = JSON.parseObject(responseData);
            logger.info("getMusicLyricById.jsonObject:{}",jsonObject);
            JSONObject data = jsonObject.getJSONObject("data");
            return data.getString("url");
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    //使用id获取音乐详情
    public String getMusicPicUrlById(Integer musicId){
        logger.info("getMusicDetailById:{}",musicId);
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "/song/detail?ids=" + musicId ;
        try {
            //获取音乐详情 http://101.42.149.18:3000/song/detail?ids=347230
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = JSON.parseObject(responseData);
            logger.info("getMusicDetailById.jsonObject:{}",jsonObject);

            JSONArray songs = jsonObject.getJSONArray("songs");
            JSONObject song = songs.getJSONObject(0);
            JSONObject al = song.getJSONObject("al");
            return al.getString("picUrl");
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    //使用id获取音乐歌词
    public String getMusicLyricById(Integer musicId){
        logger.info("getMusicLyricById:{}",musicId);
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "/lyric?id=" + musicId ;
        try {
            //获取Lyric详情 http://101.42.149.18:3000/lyric?id=33894312
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = JSON.parseObject(responseData);
            logger.info("getMusicLyricById.jsonObject:{}",jsonObject);
            JSONObject lrc = jsonObject.getJSONObject("lrc");
            return lrc.getString("lyric");
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    //使用id获取歌手详细信息
    public JSONObject getSingerDetailById(Integer singerId){
        logger.info("getSingerDetailById:{}",singerId);
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "/artists?id=" + singerId ;
        try {
            //获取歌手详情 http://101.42.149.18:3000/artists?id=6452
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = JSON.parseObject(responseData);
            JSONObject artist = null;
            try {
                artist = jsonObject.getJSONObject("artist");
            }catch (Exception e){
                // 捕获异常并忽略或记录，不做其他处理
            }

            return artist;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //通过歌单ID批量导入音乐。
    public void batchCollectionOfMusicBySongSheet(String sheetID){
        OkHttpClient client = new OkHttpClient();
        String playlistDetailUrl = baseUrl + "/playlist/detail?id=" + sheetID ;
        logger.info("playlistDetailUrl:{}",playlistDetailUrl);

        // 批量获取到歌曲name、歌曲id。歌手们"ar"的id和name，专辑"al"的id、name、picUrl。mv的id，video的vid，coverurl等信息
        //批量歌曲信息http://101.42.149.18:3000/song/url?id=3932159
        //获取url，time，size
        // 得到歌曲URL后下载音乐到本地，保存名字为id.mp3
        try {
            //获取歌单详情 http://101.42.149.18:3000/playlist/detail?id=442538213
            Request request = new Request.Builder().url(playlistDetailUrl).build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = JSON.parseObject(responseData);
            JSONObject playlist = jsonObject.getJSONObject("playlist");
            JSONArray tracks = playlist.getJSONArray("tracks");
            Sheet sheet = new Sheet();
            // 获取到歌单的name
            sheet.setName(playlist.getString("name"));
            // 获取到歌单的coverImgUrl
            sheet.setImgUrl(playlist.getString("coverImgUrl"));
            // 获取到歌单的description
            sheet.setDescription(playlist.getString("description"));
            // 设置歌单用户为管理员0
            sheet.setUser(0);
            // 设置来源为wy
            sheet.setSource("wy");
            // 设置来源id
            sheet.setSourceId(sheetID);
            // 设置公开
            sheet.setIsPublic(1);
            // 新增歌单信息。
            logger.info("sheet:{}",sheet);
            try {
                sheetService.save(sheet);
            }catch (Exception e){
                //写入失败，则说明存在该数据，取到该数据
                Map<String, Object> sheetParams = new HashMap<>();
                sheetParams.put("source", "wy");
                sheetParams.put("source_id", sheet.getSourceId());
                QueryWrapper sheetQueryWrapper= new QueryWrapper();
                sheetQueryWrapper.allEq(sheetParams);
                logger.info("写入失败，则说明存在该sheet数据，取到该数据sheetParams:{}", sheetParams);
                logger.error("写入失败错误数据:{}",e);
                Sheet sheetOne = sheetService.getOne(sheetQueryWrapper);
                logger.info("取到该数据sheetService.getOne:{}", sheetOne);
                sheet = sheetOne;
            }

            // 通过json的tracks数组获取到歌曲列表。
            for (int i = 0; i < tracks.size(); i++) {
                JSONObject track = tracks.getJSONObject(i);
                Music music = new Music();
                // 获取到歌曲name
                music.setName(track.getString("name"));
                // 获取到歌曲id
                music.setSource("wy");
                music.setSourceData(track.getInteger("id")+"");
                // 获取歌词
                String Lyric = getMusicLyricById(track.getInteger("id"));
                logger.info("Lyric:{}",Lyric);
                music.setLyric(Lyric);
                String musicPicUrl = getMusicPicUrlById(track.getInteger("id"));
                logger.info("musicPicUrl:{}",musicPicUrl);
                music.setImgUrl(musicPicUrl);
                // 获取到专辑。
                JSONObject albumObject = track.getJSONObject("al");
                Album album = new Album();
                //判断是否存在该专辑。
                Map<String, Object> albumParams = new HashMap<>();
                albumParams.put("source", "wy");
                albumParams.put("source_id", (albumObject.getInteger("id")+""));
                QueryWrapper albumQueryWrapper= new QueryWrapper();
                albumQueryWrapper.allEq(albumParams);
                logger.info("读取albumQueryWrapper:{}",albumQueryWrapper);
                Album albumOne = albumService.getOne(albumQueryWrapper);
                logger.info("读取albumOne:{}", albumOne);
                if (albumOne==null){
                    logger.info("没有当前专辑");
                    //没有当前专辑，则插入新的专辑。
                    album.setSource("wy");
                    album.setSourceId(albumObject.getInteger("id")+"");
                    album.setName(albumObject.getString("name"));
                    album.setImgUrl(albumObject.getString("picUrl"));
                    logger.info("albumService.save:{}", album);
                    albumService.save(album);
                }else {
                    logger.info("有当前专辑");
                    //有当前专辑，则赋值
                    album = albumOne;
                }
                music.setAlbum(album.getId());
                //尝试保存，保存失败则表示存在，如果存在这首歌，则获取这首歌的信息。
                try {
                    musicService.save(music);
                    logger.info("尝试保存成功musicService.save:{}", music);
                }catch (Exception e){
                    logger.info("尝试保存失败musicService.save:{}", music);
                    Map<String, Object> musicParams = new HashMap<>();
                    musicParams.put("source", "wy");
                    musicParams.put("source_data", music.getSourceData());
                    QueryWrapper musicQueryWrapper= new QueryWrapper();
                    musicQueryWrapper.allEq(musicParams);
                    logger.info("musicParams:{}", musicParams);
                    music = musicService.getOne(musicQueryWrapper);
                    logger.info("musicService.getOne:{}", music);
                }

                // 获取到歌手列表。
                JSONArray artists = track.getJSONArray("ar");
                logger.info("获取到歌手列表:{}", artists);

                //循环读取歌手
                for (int j = 0; j < artists.size(); j++) {
                    Singer singer = new Singer();
                    JSONObject artist = artists.getJSONObject(j);
                    String singerName=artist.getString("name");
                    Integer singerId = artist.getInteger("id");
                    // 先寻找是否存在当前歌手

                    QueryWrapper queryWrapper= new QueryWrapper();
                    queryWrapper.eq("name" ,singerName);
                    Singer getSinger = singerService.getOne(queryWrapper);
                    logger.info("寻找是否存在当前歌手getSinger:{}", getSinger);
                    if (getSinger==null){
                        //没有当前歌手，插入新的歌手。
                        // 获取歌手详情
                        JSONObject singerDetail = getSingerDetailById(singerId);
                        if (singerDetail!=null){
                            singer.setName(singerName);
                            singer.setDescription(singerDetail.getString("briefDesc"));
                            singer.setImgUrl(singerDetail.getString("picUrl"));
                            singer.setImg1v1Url(singerDetail.getString("img1v1Url"));
                            singerService.save(singer);
                        }else {
                            singer = new Singer();
                        }
                        // 新增歌手
                        logger.info("没有当前歌手,新增歌手:{}", singer);

                    }else {
                        singer = getSinger;
                        logger.info("有当前歌手:{}", singer);
                    }
                    //写入SingerMusic歌手音乐链接表
                    SingerMusic singerMusic = new SingerMusic();
                    singerMusic.setMusic(music.getId());
                    singerMusic.setSinger(singer.getId());
                    logger.info("写入SingerMusic歌手音乐链接表:{}", singerMusic);
                    try {
                        // 尝试插入数据
                        singerMusicService.save(singerMusic);
                    } catch (DuplicateKeyException e) {
                        // 捕获异常并忽略或记录，不做其他处理
                    }
                    //写入AlbumSinger专辑歌手链接表
                    AlbumSinger albumSinger = new AlbumSinger();
                    albumSinger.setSinger(singer.getId());
                    albumSinger.setAlbum(album.getId());

                    try {
                        // 尝试插入数据
                        albumSingerService.save(albumSinger);
                    } catch (DuplicateKeyException e) {
                        // 捕获异常并忽略或记录，不做其他处理
                    }
                }

                // 新增AlbumMusic专辑音乐链接表记录。
                AlbumMusic albumMusic = new AlbumMusic();
                albumMusic.setMusic(music.getId());
                albumMusic.setAlbum(album.getId());
                logger.info("写入AlbumMusic专辑音乐链接表:{}", albumMusic);
                try {
                    // 尝试插入数据
                    albumMusicService.save(albumMusic);
                } catch (DuplicateKeyException e) {
                    // 捕获异常并忽略或记录，不做其他处理
                }
                // 新增SheetMusic歌单音乐链接表记录
                SheetMusic sheetMusic = new SheetMusic();
                sheetMusic.setMusic(music.getId());
                sheetMusic.setSheet(sheet.getId());
                logger.info("SheetMusic歌单音乐链接表:{}", sheetMusic);
                try {
                    // 尝试插入数据
                    sheetMusicService.save(sheetMusic);
                } catch (DuplicateKeyException e) {
                    // 捕获异常并忽略或记录，不做其他处理
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
