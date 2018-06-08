package pre.cyy.download;

import pre.cyy.request.Page;
import pre.cyy.request.Request;
import pre.cyy.request.Task;


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
