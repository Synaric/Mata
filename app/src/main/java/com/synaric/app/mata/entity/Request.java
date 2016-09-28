package com.synaric.app.mata.entity;

import com.synaric.web.mata.entity.RequestExternal;

/**
 * APP端请求封装。
 * <br/><br/>Created by Synaric on 2016/9/28 0028.
 */
public class Request {

    /**
     * 请求服务端Service名。
     */
    private String service;

    /**
     * 请求参数。
     */
    private String params;

    /**
     * 请求扩展内容，例如平台、数据分页等。
     */
    private RequestExternal ext;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public RequestExternal getExt() {
        return ext;
    }

    public void setExt(RequestExternal ext) {
        this.ext = ext;
    }
}
