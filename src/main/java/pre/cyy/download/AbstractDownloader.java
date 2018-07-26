package pre.cyy.download;


import pre.cyy.request.PageResponse;
import pre.cyy.request.Request;
import pre.cyy.request.SiteBuilder;
import pre.cyy.request.Job;

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
    public PageResponse download(String url, String charset) {
        PageResponse pageResponse = download(new Request(url), SiteBuilder.me().setCharset(charset).toTask());
        return pageResponse;
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
