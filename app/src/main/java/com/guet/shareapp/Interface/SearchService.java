package com.guet.shareapp.Interface;

import com.guet.shareapp.Entity.HotTag;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by hcc on 2021/6/21 14:53
 * 1800300925 林楷浩
 * <p>
 * 搜索相关api
 */

public interface SearchService {

    /**
     * 首页发现热搜词
     */
    @GET("main/hotword")
    Observable<HotTag> getHotSearchTags();
}
