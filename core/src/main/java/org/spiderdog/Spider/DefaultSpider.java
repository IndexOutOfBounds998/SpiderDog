package org.spiderdog.Spider;

import com.google.common.base.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spiderdog.api.GenericTypeCommand;
import org.spiderdog.api.SpiderCall;
import org.spiderdog.command.CommandContext;
import org.spiderdog.download.HttpClientDownloader;
import org.spiderdog.model.PageSource;
import org.spiderdog.model.Pager;
import org.spiderdog.model.Rule;
import org.spiderdog.proxy.ProxyProvider;
import org.spiderdog.request.Job;
import org.spiderdog.request.PageResponse;
import org.spiderdog.request.Request;
import org.spiderdog.request.SiteConfigBuilder;
import org.spiderdog.utils.BeanUtils;
import org.spiderdog.utils.SearchUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * DefaultSpider
 * Created by yang on 2019/8/13.
 */
public class DefaultSpider<T> implements Runnable, Job {

    private SpiderCall spiderCall;

    private Pager pager;

    private PageSource pageSource;

    private String url;

    private SiteConfigBuilder configBuilder;

    private T souceClass;

    private Queue<String> urls = new ArrayBlockingQueue<String>(10);

    private HashMap<String, Rule> fieldAnno = new HashMap<>();

    private ProxyProvider proxyProvider;

    private CommandContext commandContext = CommandContext.getCommandContext();

    private DefaultSpider() {
    }

    public DefaultSpider(SiteConfigBuilder configBuilder, SpiderCall spiderCall) {

        this.configBuilder = configBuilder;
        this.spiderCall = spiderCall;

    }

    public DefaultSpider(SiteConfigBuilder configBuilder, SpiderCall spiderCall, T t) {
        this.configBuilder = configBuilder;
        this.spiderCall = spiderCall;
        this.souceClass = t;
    }


    public static <T> DefaultSpider createSpiderDog(SiteConfigBuilder configBuilder, SpiderCall spiderCall, T t) {

        return new DefaultSpider<T>(configBuilder, spiderCall, t);

    }

    @SuppressWarnings("unchecked")
    public DefaultSpider setSource(T souceClass) {
        this.souceClass = souceClass;
        return this;
    }

    public DefaultSpider setConfig(SiteConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    public DefaultSpider setProxy(ProxyProvider proxy) {
        this.proxyProvider = proxy;
        return this;
    }

    /**
     * 解析
     */
    void resolveAnnotation() {
        this.url = SearchUtil.getUrl(this.souceClass);
        this.pager = SearchUtil.getNextPager(this.souceClass);
        this.pageSource = SearchUtil.getPageSource(this.souceClass);
        this.fieldAnno = SearchUtil.getFieldAnno(this.souceClass);
        this.urls.add(this.url);
    }


    @Override
    public void run() {
        resolveAnnotation();

        while (true) {

            this.url = urls.poll();
            if (Strings.isNullOrEmpty(this.url)) {
                System.out.println("等待url。。。");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            requestAndParse();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * requestAndParse 请求并解析
     */
    private void requestAndParse() {
        HttpClientDownloader clientDownloader = new HttpClientDownloader();
        if (proxyProvider != null) {
            clientDownloader.setProxyProvider(proxyProvider);
        }
        Request request = new Request(url);
        PageResponse download = clientDownloader.download(request, this);
        Document document = Jsoup.parse(download.getRawText());

        //获取下一页加载到urls里
        if (this.pager != null) {
            Elements select = document.select(pager.getSeletor());
            if (select != null) {
                String nextUrl = select.attr(pager.getAttr());
                if (!Strings.isNullOrEmpty(nextUrl)) {
                    System.out.println("add next url " + nextUrl);
                    this.urls.offer(nextUrl);
                }
            }

        }

        HashMap<String, Object> map = new HashMap<>();
        Set<Map.Entry<String, Rule>> entries = fieldAnno.entrySet();
        for (Map.Entry<String, Rule> entry : entries) {
            GenericTypeCommand genericTypeCommand = commandContext.genericTypeCommand(entry.getValue().getType());
            Object parseText = genericTypeCommand.command(entry.getValue(), document);
            map.put(entry.getKey(), parseText);
        }


        //检查当前页面是否需要获取更多的链接

        if (pageSource != null) {
            if (!Strings.isNullOrEmpty(pageSource.getSeletor())) {

                Elements select = document.select(pageSource.getSeletor());

                for (Element element : select) {

                    String url = element.attr(pageSource.getAttr());

                    if (!Strings.isNullOrEmpty(url)) {
                        System.out.println("add url " + url);
                        this.urls.offer(url);
                    }

                }


            }
        }

        this.souceClass = BeanUtils.mapToEntity(map, (Class<T>) this.souceClass.getClass());
        this.spiderCall.onSuccess(this.souceClass);
    }

    @Override
    public String getUUID() {
        return null;
    }

    @Override
    public SiteConfigBuilder getSite() {
        return configBuilder;
    }
}
