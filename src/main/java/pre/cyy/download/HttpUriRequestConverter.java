package pre.cyy.download;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import pre.cyy.proxy.Proxy;
import pre.cyy.request.Request;
import pre.cyy.request.SiteBuilder;
import pre.cyy.utils.HttpConstant;
import pre.cyy.utils.UrlUtils;

import java.util.Map;

/**
 * @author yang
 * @date 2018/7/24 18:31
 * @description HttpUriRequestConverter
 */
public class HttpUriRequestConverter {

    public HttpClientRequestContext convert(Request request, SiteBuilder siteBuilder, Proxy proxy) {
        HttpClientRequestContext httpClientRequestContext = new HttpClientRequestContext();
        httpClientRequestContext.setHttpUriRequest(convertHttpUriRequest(request, siteBuilder, proxy));
        httpClientRequestContext.setHttpClientContext(convertHttpClientContext(request, siteBuilder, proxy));
        return httpClientRequestContext;
    }

    private HttpClientContext convertHttpClientContext(Request request, SiteBuilder siteBuilder, Proxy proxy) {
        HttpClientContext httpContext = new HttpClientContext();
        if (proxy != null && proxy.getUsername() != null) {
            AuthState authState = new AuthState();
            authState.update(new BasicScheme(), new UsernamePasswordCredentials(proxy.getUsername(), proxy.getPassword()));
            httpContext.setAttribute(HttpClientContext.PROXY_AUTH_STATE, authState);
        }
        if (request.getCookies() != null && !request.getCookies().isEmpty()) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Map.Entry<String, String> cookieEntry : request.getCookies().entrySet()) {
                BasicClientCookie cookie1 = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
                cookie1.setDomain(UrlUtils.removePort(UrlUtils.getDomain(request.getUrl())));
                cookieStore.addCookie(cookie1);
            }
            httpContext.setCookieStore(cookieStore);
        }
        return httpContext;
    }

    private HttpUriRequest convertHttpUriRequest(Request request, SiteBuilder siteBuilder, Proxy proxy) {
        RequestBuilder requestBuilder = selectRequestMethod(request).setUri(request.getUrl());
        if (siteBuilder.getHeaders() != null) {
            for (Map.Entry<String, String> headerEntry : siteBuilder.getHeaders().entrySet()) {
                requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (siteBuilder != null) {
            requestConfigBuilder.setConnectionRequestTimeout(siteBuilder.getTimeOut())
                    .setSocketTimeout(siteBuilder.getTimeOut())
                    .setConnectTimeout(siteBuilder.getTimeOut())
                    .setCookieSpec(CookieSpecs.STANDARD);
        }

        if (proxy != null) {
            requestConfigBuilder.setProxy(new HttpHost(proxy.getHost(), proxy.getPort()));
        }
        requestBuilder.setConfig(requestConfigBuilder.build());
        HttpUriRequest httpUriRequest = requestBuilder.build();
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                httpUriRequest.addHeader(header.getKey(), header.getValue());
            }
        }
        return httpUriRequest;
    }

    private RequestBuilder selectRequestMethod(Request request) {
        String method = request.getMethod();
        if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
            //default get
            return RequestBuilder.get();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
            return addFormParams(RequestBuilder.post(), request);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
            return RequestBuilder.head();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
            return addFormParams(RequestBuilder.put(), request);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
            return RequestBuilder.delete();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
            return RequestBuilder.trace();
        }
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }

    private RequestBuilder addFormParams(RequestBuilder requestBuilder, Request request) {
        if (request.getRequestBody() != null) {
            ByteArrayEntity entity = new ByteArrayEntity(request.getRequestBody().getBody());
            entity.setContentType(request.getRequestBody().getContentType());
            requestBuilder.setEntity(entity);
        }
        return requestBuilder;
    }

}
