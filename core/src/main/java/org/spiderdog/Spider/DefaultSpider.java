package org.spiderdog.Spider;

import com.google.common.base.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.spiderdog.api.SpiderCall;
import org.spiderdog.download.HttpClientDownloader;
import org.spiderdog.model.Rule;
import org.spiderdog.request.SiteConfigBuilder;
import org.spiderdog.utils.BeanUtils;
import org.spiderdog.utils.SearchUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * DefaultSpider
 * Created by yang on 2019/8/13.
 */
public class DefaultSpider<T> implements Runnable {

    private SpiderCall spiderCall;

    private String url;

    private SiteConfigBuilder configBuilder;

    private T souceClass;

    HashMap<String, Rule> fieldAnno = new HashMap<>();

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

    /**
     * 解析
     */
    void resolveAnnotation() {
        this.url = SearchUtil.getUrl(this.souceClass);
        this.fieldAnno = SearchUtil.getFieldAnno(this.souceClass);

    }


    @Override
    public void run() {
        resolveAnnotation();
        requestAndParse();
    }

    private void requestAndParse() {
        HttpClientDownloader clientDownloader = new HttpClientDownloader();
        String download = clientDownloader.download(url);
        Document document = Jsoup.parse(download);
        HashMap<String, Object> map = new HashMap<>();
        Set<Map.Entry<String, Rule>> entries = fieldAnno.entrySet();
        for (Map.Entry<String, Rule> entry : entries) {
            String key = entry.getKey();
            Rule value = entry.getValue();
            String xpath = value.getXpath();
            String seletor = value.getSeletor();
            if (!Strings.isNullOrEmpty(xpath)) {

            }
            if (!Strings.isNullOrEmpty(seletor)) {
                String parseText = "";
                Elements select = document.select(seletor);
                if (Strings.isNullOrEmpty(value.getAttr())) {
                    parseText = select.text();
                } else {
                    parseText = select.attr(value.getAttr());
                }
                map.put(key, parseText);
            }

        }
        this.souceClass = BeanUtils.mapToEntity(map, (Class<T>) this.souceClass.getClass());
        this.spiderCall.onSuccess(this.souceClass);
    }
}
