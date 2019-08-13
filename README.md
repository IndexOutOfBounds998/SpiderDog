**spiderDog**
**代码实例**
```java
import com.google.common.base.Strings;
import org.spiderdog.Spider.DefaultSpider;
import org.spiderdog.api.SpiderCall;
import org.spiderdog.proxy.Proxy;
import org.spiderdog.proxy.ProxyProvider;
import org.spiderdog.proxy.SimpleProxyProvider;
import org.spiderdog.request.SiteConfigBuilder;

/**
 * Main
 * Created by yang on 2019/8/13.
 */
public class Main {


    public static void main(String[] args) {

        SiteConfigBuilder siteConfigBuilder = new SiteConfigBuilder();

        Proxy proxy = new Proxy("127.0.0.1", 1088);
        ProxyProvider proxyProvider = new SimpleProxyProvider(proxy);

        DefaultSpider.createSpiderDog(null, new SpiderCall<Model>() {
            @Override
            public void onSuccess(Model model) {
                if (!Strings.isNullOrEmpty(model.getImg())) {
                    System.out.println(model.getTitle());
                    System.out.println(model.getImg());
                }
            }
        }, new Model())
                .setConfig(siteConfigBuilder)
                .setProxy(proxyProvider)
                .run();
    }
}

     
```
