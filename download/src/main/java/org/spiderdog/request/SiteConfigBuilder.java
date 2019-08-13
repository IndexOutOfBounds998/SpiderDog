package org.spiderdog.request;

import org.spiderdog.utils.HttpConstant;

import java.util.*;

/**
 * @author yang
 * @date 2018/7/24 18:22
 * @description SiteConfigBuilder 站点信息
 */
public class SiteConfigBuilder {

    private String domain;

    private String userAgent;

    private Map<String, String> defaultCookies = new LinkedHashMap<String, String>();

    private Map<String, Map<String, String>> cookies = new HashMap<String, Map<String, String>>();

    private String charset;


    private int retryTimes = 0;

    private int cycleRetryTimes = 0;

    private int retrySleepTime = 1000;

    private int timeOut = 5000;

    private static final Set<Integer> DEFAULT_STATUS_CODE_SET = new HashSet<Integer>();

    private Set<Integer> acceptStatCode = DEFAULT_STATUS_CODE_SET;

    private Map<String, String> headers = new HashMap<String, String>();

    private boolean useGzip = true;

    private boolean disableCookieManagement = false;

    static {
        DEFAULT_STATUS_CODE_SET.add(HttpConstant.StatusCode.CODE_200);
    }

    /**
     * new a SiteConfigBuilder
     *
     * @return new site
     */
    public static SiteConfigBuilder builder() {
        return new SiteConfigBuilder();
    }

    /**
     * Add a cookie with domain {@link #getDomain()}
     *
     * @param name  name
     * @param value value
     * @return this
     */
    public SiteConfigBuilder addCookie(String name, String value) {
        defaultCookies.put(name, value);
        return this;
    }

    /**
     * Add a cookie with specific domain.
     *
     * @param domain domain
     * @param name   name
     * @param value  value
     * @return this
     */
    public SiteConfigBuilder addCookie(String domain, String name, String value) {
        if (!cookies.containsKey(domain)) {
            cookies.put(domain, new HashMap<String, String>());
        }
        cookies.get(domain).put(name, value);
        return this;
    }

    /**
     * set user agent
     *
     * @param userAgent userAgent
     * @return this
     */
    public SiteConfigBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    /**
     * get cookies
     *
     * @return get cookies
     */
    public Map<String, String> getCookies() {
        return defaultCookies;
    }

    /**
     * get cookies of all domains
     *
     * @return get cookies
     */
    public Map<String, Map<String, String>> getAllCookies() {
        return cookies;
    }

    /**
     * get user agent
     *
     * @return user agent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * get domain
     *
     * @return get domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * set the domain of site.
     *
     * @param domain domain
     * @return this
     */
    public SiteConfigBuilder setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    /**
     * Set charset of page manually.<br>
     * When charset is not set or set to null, it can be auto detected by Http header.
     *
     * @param charset charset
     * @return this
     */
    public SiteConfigBuilder setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * get charset set manually
     *
     * @return charset
     */
    public String getCharset() {
        return charset;
    }

    public int getTimeOut() {
        return timeOut;
    }

    /**
     * set timeout for downloader in ms
     *
     * @param timeOut timeOut
     * @return this
     */
    public SiteConfigBuilder setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    /**
     * Set acceptStatCode.<br>
     * When status code of http response is in acceptStatCodes, it will be processed.<br>
     * {200} by default.<br>
     * It is not necessarily to be set.<br>
     *
     * @param acceptStatCode acceptStatCode
     * @return this
     */
    public SiteConfigBuilder setAcceptStatCode(Set<Integer> acceptStatCode) {
        this.acceptStatCode = acceptStatCode;
        return this;
    }

    /**
     * get acceptStatCode
     *
     * @return acceptStatCode
     */
    public Set<Integer> getAcceptStatCode() {
        return acceptStatCode;
    }


    /**
     * Get retry times immediately when org.spiderdog.download fail, 0 by default.<br>
     *
     * @return retry times when org.spiderdog.download fail
     */
    public int getRetryTimes() {
        return retryTimes;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Put an Http header for downloader. <br>
     * Use {@link #addCookie(String, String)} for cookie and {@link #setUserAgent(String)} for user-agent. <br>
     *
     * @param key   key of http header, there are some keys constant in {@link HttpConstant.Header}
     * @param value value of header
     * @return this
     */
    public SiteConfigBuilder addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    /**
     * Set retry times when org.spiderdog.download fail, 0 by default.<br>
     *
     * @param retryTimes retryTimes
     * @return this
     */
    public SiteConfigBuilder setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    /**
     * When cycleRetryTimes is more than 0, it will add back to scheduler and try org.spiderdog.download again. <br>
     *
     * @return retry times when org.spiderdog.download fail
     */
    public int getCycleRetryTimes() {
        return cycleRetryTimes;
    }

    /**
     * Set cycleRetryTimes times when org.spiderdog.download fail, 0 by default. <br>
     *
     * @param cycleRetryTimes cycleRetryTimes
     * @return this
     */
    public SiteConfigBuilder setCycleRetryTimes(int cycleRetryTimes) {
        this.cycleRetryTimes = cycleRetryTimes;
        return this;
    }

    public boolean isUseGzip() {
        return useGzip;
    }

    public int getRetrySleepTime() {
        return retrySleepTime;
    }

    /**
     * Set retry sleep times when org.spiderdog.download fail, 1000 by default. <br>
     *
     * @param retrySleepTime retrySleepTime
     * @return this
     */
    public SiteConfigBuilder setRetrySleepTime(int retrySleepTime) {
        this.retrySleepTime = retrySleepTime;
        return this;
    }

    /**
     * Whether use gzip. <br>
     * Default is true, you can set it to false to disable gzip.
     *
     * @param useGzip useGzip
     * @return this
     */
    public SiteConfigBuilder setUseGzip(boolean useGzip) {
        this.useGzip = useGzip;
        return this;
    }

    public boolean isDisableCookieManagement() {
        return disableCookieManagement;
    }

    /**
     * Downloader is supposed to store response cookie.
     * Disable it to ignore all cookie fields and stay clean.
     * Warning: Set cookie will still NOT work if disableCookieManagement is true.
     *
     * @param disableCookieManagement disableCookieManagement
     * @return this
     */
    public SiteConfigBuilder setDisableCookieManagement(boolean disableCookieManagement) {
        this.disableCookieManagement = disableCookieManagement;
        return this;
    }

    public Job toJob() {
        return new Job() {
            @Override
            public String getUUID() {
                String uuid = SiteConfigBuilder.this.getDomain();
                if (uuid == null) {
                    uuid = UUID.randomUUID().toString();
                }
                return uuid;
            }

            @Override
            public SiteConfigBuilder getSite() {
                return SiteConfigBuilder.this;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SiteConfigBuilder siteConfigBuilder = (SiteConfigBuilder) o;

        if (cycleRetryTimes != siteConfigBuilder.cycleRetryTimes) return false;
        if (retryTimes != siteConfigBuilder.retryTimes) return false;
        if (timeOut != siteConfigBuilder.timeOut) return false;
        if (acceptStatCode != null ? !acceptStatCode.equals(siteConfigBuilder.acceptStatCode) : siteConfigBuilder.acceptStatCode != null)
            return false;
        if (charset != null ? !charset.equals(siteConfigBuilder.charset) : siteConfigBuilder.charset != null) return false;
        if (defaultCookies != null ? !defaultCookies.equals(siteConfigBuilder.defaultCookies) : siteConfigBuilder.defaultCookies != null)
            return false;
        if (domain != null ? !domain.equals(siteConfigBuilder.domain) : siteConfigBuilder.domain != null) return false;
        if (headers != null ? !headers.equals(siteConfigBuilder.headers) : siteConfigBuilder.headers != null) return false;
        return userAgent != null ? userAgent.equals(siteConfigBuilder.userAgent) : siteConfigBuilder.userAgent == null;
    }

    @Override
    public int hashCode() {
        int result = domain != null ? domain.hashCode() : 0;
        result = 31 * result + (userAgent != null ? userAgent.hashCode() : 0);
        result = 31 * result + (defaultCookies != null ? defaultCookies.hashCode() : 0);
        result = 31 * result + (charset != null ? charset.hashCode() : 0);

        result = 31 * result + retryTimes;
        result = 31 * result + cycleRetryTimes;
        result = 31 * result + timeOut;
        result = 31 * result + (acceptStatCode != null ? acceptStatCode.hashCode() : 0);
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SiteConfigBuilder{" +
                "domain='" + domain + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", cookies=" + defaultCookies +
                ", charset='" + charset + '\'' +
                ", retryTimes=" + retryTimes +
                ", cycleRetryTimes=" + cycleRetryTimes +
                ", timeOut=" + timeOut +
                ", acceptStatCode=" + acceptStatCode +
                ", headers=" + headers +
                '}';
    }

}
