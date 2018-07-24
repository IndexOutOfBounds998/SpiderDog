package pre.cyy.download;

import pre.cyy.request.Page;
import pre.cyy.request.Request;
import pre.cyy.request.Task;

/**
 * @author yang
 * @date 2018/7/24 18:18
 * @description Downloader 接口
 */
public interface Downloader {

    /**
     * Downloads web pages and store in Page object.
     *
     * @param request request
     * @param task    task
     * @return page
     */
    Page download(Request request, Task task);

}
