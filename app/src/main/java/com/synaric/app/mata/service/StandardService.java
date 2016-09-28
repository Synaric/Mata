package com.synaric.app.mata.service;

import com.synaric.app.mata.BuildConfig;

import retrofit2.http.Field;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 请求网络接口。
 * 访问方式
 * <a href="http://localhost:8088/mata-service/dispatcher.do?service=userService&params={}&ext={}"/>sample url</a>
 * <br/><br/>Created by Synaric on 2016/9/28 0028.
 */
public interface StandardService {

    /**
     * 普通网络访问URL。
     */
    String BASE_URL = "http://" + BuildConfig.HOST + "/mata-service/dispatcher.do";

    @POST
    Observable<String> login(@Field("service") String service, @Field("params") String params, @Field("ext") String ext);
}
