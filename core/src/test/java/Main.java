import com.google.common.base.Strings;
import org.spiderdog.Spider.DefaultSpider;
import org.spiderdog.api.SpiderCall;
import org.spiderdog.collection.DefaultUrlCollection;
import org.spiderdog.proxy.Proxy;
import org.spiderdog.proxy.ProxyProvider;
import org.spiderdog.proxy.SimpleProxyProvider;
import org.spiderdog.request.SiteConfigBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Main
 * Created by yang on 2019/8/13.
 */
public class Main {


    public static void main(String[] args) {

        List<Model> list = new ArrayList<>();

        SiteConfigBuilder siteConfigBuilder = new SiteConfigBuilder();

        Proxy proxy = new Proxy("127.0.0.1", 1088);
        ProxyProvider proxyProvider = new SimpleProxyProvider(proxy);

        DefaultSpider.createSpiderDog(null, new SpiderCall<Model>() {
            @Override
            public void onSuccess(Model model) {
                if (!Strings.isNullOrEmpty(model.getImg())) {
                    list.add(model);
                }
            }
        }, new Model())
                .setConfig(siteConfigBuilder)
                .setProxy(proxyProvider)
                .setCollection(new DefaultUrlCollection(100))
                .run(2);

        list.forEach(model -> System.out.println(model.getImg()));
    }

}
