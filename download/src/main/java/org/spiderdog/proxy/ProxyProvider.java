package org.spiderdog.proxy;


import org.spiderdog.request.PageResponse;
import org.spiderdog.request.Job;

/**
 * Proxy provider. <br>
 *
 * @since 0.7.0
 */
public interface ProxyProvider {

    /**
     * Return org.spiderdog.proxy to Provider when complete a org.spiderdog.download.
     *
     * @param proxy        the org.spiderdog.proxy config contains host,port and identify info
     * @param pageResponse the org.spiderdog.download result
     * @param job          the org.spiderdog.download job
     */
    void returnProxy(Proxy proxy, PageResponse pageResponse, Job job);

    /**
     * Get a org.spiderdog.proxy for job by some strategy.
     *
     * @param job the org.spiderdog.download job
     * @return org.spiderdog.proxy
     */
    Proxy getProxy(Job job);

}
