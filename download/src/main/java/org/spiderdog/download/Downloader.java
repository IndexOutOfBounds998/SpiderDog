package org.spiderdog.download;

import org.spiderdog.request.PageResponse;
import org.spiderdog.request.Request;
import org.spiderdog.request.Job;

/**
 * @author yang
 * @date 2018/7/24 18:18
 * @description Downloader 接口
 */
public interface Downloader {

    /**
     * Downloads web pages and store in PageResponse object.
     *
     * @param request org.spiderdog.request
     * @param job    job
     * @return page
     */
    PageResponse download(Request request, Job job);

}
