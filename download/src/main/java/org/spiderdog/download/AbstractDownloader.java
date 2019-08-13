package org.spiderdog.download;


import org.spiderdog.request.PageResponse;
import org.spiderdog.request.Request;
import org.spiderdog.request.SiteConfigBuilder;
import org.spiderdog.request.Job;

/**
 * @author yang
 * @date 2018/7/24 18:17
 * @description AbstractDownloader downLoader 基类
 */
public abstract class AbstractDownloader implements Downloader {

    /**
     * A simple method to org.spiderdog.download a url.
     *
     * @param url url
     * @return html
     */
    public String download(String url) {
        return download(url, "utf-8").getRawText();
    }

    /**
     * A simple method to org.spiderdog.download a url.
     *
     * @param url     url
     * @param charset charset
     * @return html
     */
    public PageResponse download(String url, String charset) {
        return download(new Request(url), SiteConfigBuilder.builder().setCharset(charset).toJob());
    }

    @Override
    public PageResponse download(Request request, Job job) {
        return download(request, job);
    }

    protected void onSuccess(Request request) {
    }

    protected void onError(Request request) {
    }

}
