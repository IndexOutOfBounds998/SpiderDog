**请求网络**
**代码实例**
```java
import pre.cyy.download.HttpClientDownloader;
import pre.cyy.proxy.SimpleProxyProvider;
import pre.cyy.request.HttpRequestBody;
import pre.cyy.request.PageResponse;
import pre.cyy.request.Request;
import pre.cyy.request.SiteConfigBuilder;
import pre.cyy.utils.HttpConstant;

import java.io.UnsupportedEncodingException;

/**
 * @Author: yang
 * @Date: 2018/6/20.19:03
 * @Desc: to do?
 */
public class test {
    public static void main(String[] args) {


        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //一个简单的请求
        String str = httpClientDownloader.download("https://blog.csdn.net/xwnxwn/article/details/52510484");
        System.out.println(str);

        //设置代理
        httpClientDownloader.setProxyProvider(new SimpleProxyProvider(null));

        Request request = new Request();
        request.setUrl("https://blog.csdn.net/xwnxwn/article/details/52510484");
        //get
        request.setMethod(HttpConstant.Method.GET);
        SiteConfigBuilder siteConfigBuilder = SiteConfigBuilder.builder();
        siteConfigBuilder.addHeader("Accept-Encoding", "gzip, deflate");
        siteConfigBuilder.setCharset("utf-8");
        siteConfigBuilder.setDomain("blog.csdn.net");
        siteConfigBuilder.setTimeOut(5000);
        siteConfigBuilder.addCookie("cookie", "11111111111");
        PageResponse pageResponse = httpClientDownloader.download(request, siteConfigBuilder.toJob());
        System.out.println(pageResponse.getRawText());


        //post
        Request requestPost = new Request();
        request.setUrl("https://blog.csdn.net/xwnxwn/article/details/52510484");
        requestPost.setMethod(HttpConstant.Method.POST);
        try {
            requestPost.setRequestBody(HttpRequestBody.json(null, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置站点
        SiteConfigBuilder sitepost = SiteConfigBuilder.builder();
        siteConfigBuilder.addHeader("Accept-Encoding", "gzip, deflate");
        siteConfigBuilder.setCharset("utf-8");
        siteConfigBuilder.setDomain("blog.csdn.net");
        siteConfigBuilder.setTimeOut(5000);
        siteConfigBuilder.addCookie("cookie", "11111111111");
        PageResponse pageResponsePost = httpClientDownloader.download(request, sitepost.toJob());
        System.out.println(pageResponsePost.getRawText());
    }
}

     
```
