package download;

import request.Page;
import request.Request;
import request.Task;


public interface Downloader {

    /**
     * Downloads web pages and store in Page object.
     *
     * @param request request
     * @param task    task
     * @return page
     */
    public Page download(Request request, Task task);

}
