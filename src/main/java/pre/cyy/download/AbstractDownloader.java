package pre.cyy.download;


import pre.cyy.request.Page;
import pre.cyy.request.Request;
import pre.cyy.request.Site;
import pre.cyy.request.Task;

/**
 * @author yang
 * @date 2018/7/24 18:17
 * @description AbstractDownloader downLoader 基类
 */
public abstract class AbstractDownloader implements Downloader {

    /**
     * A simple method to download a url.
     *
     * @param url url
     * @return html
     */
    public String download(String url) {
        return download(url, "utf-8").getRawText();
    }


    /**
     * A simple method to download a url.
     *
     * @param url     url
     * @param charset charset
     * @return html
     */
    public Page download(String url, String charset) {
        Page page = download(new Request(url), Site.me().setCharset(charset).toTask());
        return page;
    }

    @Override
    public Page download(Request request, Task task) {
        return download(request, task);
    }

    protected void onSuccess(Request request) {
    }

    protected void onError(Request request) {
    }

}
