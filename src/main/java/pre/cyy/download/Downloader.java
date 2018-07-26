package pre.cyy.download;

import pre.cyy.request.PageResponse;
import pre.cyy.request.Request;
import pre.cyy.request.Job;

/**
 * @author yang
 * @date 2018/7/24 18:18
 * @description Downloader 接口
 */
public interface Downloader {

    /**
     * Downloads web pages and store in PageResponse object.
     *
     * @param request request
     * @param job    job
     * @return page
     */
    PageResponse download(Request request, Job job);

}
