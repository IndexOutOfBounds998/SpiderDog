import pre.cyy.download.HttpClientDownloader;
import pre.cyy.request.Page;
import pre.cyy.request.Request;
import pre.cyy.request.Site;

/**
 * @Author: yang
 * @Date: 2018/6/20.19:03
 * @Desc: to do?
 */
public class test {
    public static void main(String[] args) {


        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        Page page = httpClientDownloader.download(new Request("https://blog.csdn.net/xwnxwn/article/details/52510484"), Site.me().toTask());
        System.out.println(page.getRawText());
    }
}
