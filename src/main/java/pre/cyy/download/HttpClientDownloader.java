package pre.cyy.download;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import pre.cyy.proxy.Proxy;
import pre.cyy.proxy.ProxyProvider;
import pre.cyy.request.Job;
import pre.cyy.request.PageResponse;
import pre.cyy.request.Request;
import pre.cyy.request.SiteConfigBuilder;
import pre.cyy.utils.CharsetUtils;
import pre.cyy.utils.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author yang
 * @date 2018/7/24 18:19
 * @description HttpClientDownloader
 */
public class HttpClientDownloader extends AbstractDownloader {

    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();

    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

    private HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();

    private ProxyProvider proxyProvider;

    private boolean responseHeader = true;

    public void setHttpUriRequestConverter(HttpUriRequestConverter httpUriRequestConverter) {
        this.httpUriRequestConverter = httpUriRequestConverter;
    }

    /**
     * @author yang
     * @date 2018/7/24 18:19
     * @description 设置代理
     */
    public void setProxyProvider(ProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }

    private CloseableHttpClient getHttpClient(SiteConfigBuilder siteConfigBuilder) {
        if (siteConfigBuilder == null) {
            return httpClientGenerator.getClient(null);
        }
        String domain = siteConfigBuilder.getDomain();
        CloseableHttpClient httpClient = httpClients.get(domain);
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(siteConfigBuilder);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }

    @Override
    public PageResponse download(Request request, Job job) {
        if (job == null || job.getSite() == null) {
            throw new NullPointerException("job or site can not be null");
        }
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = getHttpClient(job.getSite());
        Proxy proxy = proxyProvider != null ? proxyProvider.getProxy(job) : null;
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(request, job.getSite(), proxy);
        PageResponse pageResponse = PageResponse.fail();
        try {
            httpResponse = httpClient.execute(requestContext.getHttpUriRequest(), requestContext.getHttpClientContext());
            pageResponse = handleResponse(request, job.getSite().getCharset(), httpResponse, job);
            onSuccess(request);
            return pageResponse;
        } catch (IOException e) {
            onError(request);
            return pageResponse;
        } finally {
            if (httpResponse != null) {
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
            if (proxyProvider != null && proxy != null) {
                proxyProvider.returnProxy(proxy, pageResponse, job);
            }
        }
    }

    protected PageResponse handleResponse(Request request, String charset, HttpResponse httpResponse, Job job) throws IOException {
        String content = getResponseContent(charset, httpResponse);
        PageResponse pageResponse = new PageResponse();
        pageResponse.setRawText(content);
        pageResponse.setUrl((request.getUrl()));
        pageResponse.setRequest(request);
        pageResponse.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        pageResponse.setDownloadSuccess(true);
        if (responseHeader) {
            pageResponse.setHeaders(HttpClientUtils.convertHeaders(httpResponse.getAllHeaders()));
        }
        return pageResponse;
    }

    private String getResponseContent(String charset, HttpResponse httpResponse) throws IOException {
        if (charset == null) {
            byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
            String htmlCharset = getHtmlCharset(httpResponse, contentBytes);
            if (htmlCharset != null) {
                return new String(contentBytes, htmlCharset);
            } else {
                return new String(contentBytes);
            }
        } else {
            return IOUtils.toString(httpResponse.getEntity().getContent(), charset);
        }
    }

    private String getHtmlCharset(HttpResponse httpResponse, byte[] contentBytes) throws IOException {
        return CharsetUtils.detectCharset(httpResponse.getEntity().getContentType() == null ? "" : httpResponse.getEntity().getContentType().getValue(), contentBytes);
    }
}
