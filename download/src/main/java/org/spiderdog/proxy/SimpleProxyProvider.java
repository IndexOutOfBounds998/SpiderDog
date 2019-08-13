package org.spiderdog.proxy;


import com.google.common.collect.Lists;
import org.spiderdog.request.Job;
import org.spiderdog.request.PageResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yang
 * @date 2018/8/3 14:07
 * @description SimpleProxyProvider
 */
public class SimpleProxyProvider implements ProxyProvider {

    private final List<Proxy> proxies;

    private final AtomicInteger pointer;

    public SimpleProxyProvider(List<Proxy> proxies) {
        this(proxies, new AtomicInteger(-1));
    }

    public SimpleProxyProvider(Proxy proxie) {
        this(proxie, new AtomicInteger(-1));
    }


    private SimpleProxyProvider(List<Proxy> proxies, AtomicInteger pointer) {
        this.proxies = proxies;
        this.pointer = pointer;
    }

    private SimpleProxyProvider(Proxy proxies, AtomicInteger pointer) {
        List<Proxy> list = new ArrayList<>();
        list.add(proxies);
        this.proxies = list;
        this.pointer = pointer;
    }

    public static SimpleProxyProvider from(Proxy... proxies) {
        List<Proxy> proxiesTemp = new ArrayList<Proxy>(proxies.length);
        for (Proxy proxy : proxies) {
            proxiesTemp.add(proxy);
        }
        return new SimpleProxyProvider(Collections.unmodifiableList(proxiesTemp));
    }

    @Override
    public void returnProxy(Proxy proxy, PageResponse pageResponse, Job job) {
        //Donothing
    }

    @Override
    public Proxy getProxy(Job job) {
        return proxies.get(incrForLoop());
    }

    private int incrForLoop() {
        int p = pointer.incrementAndGet();
        int size = proxies.size();
        if (p < size) {
            return p;
        }
        while (!pointer.compareAndSet(p, p % size)) {
            p = pointer.get();
        }
        return p % size;
    }
}
