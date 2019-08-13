package org.spiderdog.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.spiderdog.utils.HttpConstant;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 * @date 2018/7/26 15:14
 * @description PageResponse 返回结果
 */
public class PageResponse {

    private Request request;

    private String url;

    private String rawText;


    private Map<String, List<String>> headers;

    private int statusCode = HttpConstant.StatusCode.CODE_200;

    private boolean downloadSuccess = true;


    public PageResponse() {
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public static PageResponse fail() {
        PageResponse pageResponse = new PageResponse();
        pageResponse.setDownloadSuccess(false);
        return pageResponse;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject toJsonObject() {
        return JSON.parseObject(getRawText());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRawText() {
        return rawText;
    }

    public PageResponse setRawText(String rawText) {
        this.rawText = rawText;
        return this;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public boolean isDownloadSuccess() {
        return downloadSuccess;
    }

    public void setDownloadSuccess(boolean downloadSuccess) {
        this.downloadSuccess = downloadSuccess;
    }

}
