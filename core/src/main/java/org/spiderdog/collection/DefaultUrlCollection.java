package org.spiderdog.collection;

import com.google.common.base.Strings;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * DefaultUrlCollection
 * Created by yang on 2019/8/17.
 */
public class DefaultUrlCollection implements UrlCollection {


    private int capacity = 10;

    public DefaultUrlCollection(int capacity) {
        this.capacity = capacity;
    }

    public DefaultUrlCollection() {
    }

    private Queue<String> urls = new ArrayBlockingQueue<String>(capacity);


    @Override
    public void putUrl(String url) {
        if (!Strings.isNullOrEmpty(url)) {
            urls.add(url);
        }
    }

    @Override
    public String getUrl() {
        return urls.poll();
    }
}
