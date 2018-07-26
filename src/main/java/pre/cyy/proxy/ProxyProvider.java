package pre.cyy.proxy;


import pre.cyy.request.PageResponse;
import pre.cyy.request.Job;

/**
 * Proxy provider. <br>
 *
 * @since 0.7.0
 */
public interface ProxyProvider {

    /**
     * Return proxy to Provider when complete a download.
     *
     * @param proxy the proxy config contains host,port and identify info
     * @param pageResponse  the download result
     * @param job  the download job
     */
    void returnProxy(Proxy proxy, PageResponse pageResponse, Job job);

    /**
     * Get a proxy for job by some strategy.
     *
     * @param job the download job
     * @return proxy
     */
    Proxy getProxy(Job job);

}
