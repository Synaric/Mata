package com.synaric.app.mata.service;

import com.synaric.app.mata.BuildConfig;

import retrofit2.http.Field;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 请求网络接口。
 * 访问方式：
 * <a href="http://localhost:8088/mata-service/dispatcher/emptyService?params={}&ext={}"/>sample url</a>
 * <br/><br/>Created by Synaric on 2016/9/28 0028.
 */
public interface StandardModel {

    /**
     * 普通网络访问URL。
     */
    String BASE_URL = "http://" + BuildConfig.HOST + "/mata-service/dispatcher";

    /**
     * 检查是否能成功请求服务端。成功则返回"request: ok"。
     */
    @POST("/emptyService")
    Observable<String> emptyRequest(@Field("params") String params, @Field("ext") String ext);

    /**
     * 用户登录。
     */
    @POST("/userService")
    Observable<String> login(@Field("params") String params, @Field("ext") String ext);
}