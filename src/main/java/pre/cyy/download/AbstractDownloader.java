package pre.cyy.download;


import pre.cyy.request.Page;
import pre.cyy.request.Request;
import pre.cyy.request.Site;
import pre.cyy.request.Task;

/**
 * Base class of downloader with some common methods.
 *
 * @author code4crafter@gmail.com
 * @since 0.5.0
 */
public abstract class AbstractDownloader implements Downloader {

    /**
     * A simple method to download a url.
     *
     * @param url url
     * @return html
     */
    public String download(String url) {
        return download(url, null).getRawText();
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
        return null;
    }

    protected void onSuccess(Request request) {
    }

    protected void onError(Request request) {
    }

}
