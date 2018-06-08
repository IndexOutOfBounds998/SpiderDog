package request;

import org.apache.commons.lang3.StringUtils;
import us.codecraft.xsoup.xevaluator.ElementOperator;
import utils.HttpConstant;
import utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @since 0.1.0
 */
public class Page {

    private Request request;


    private String rawText;


    private Map<String, List<String>> headers;

    private int statusCode = HttpConstant.StatusCode.CODE_200;

    private boolean downloadSuccess = true;

    private List<Request> targetRequests = new ArrayList<Request>();

    public Page() {
    }

    public static Page fail() {
        Page page = new Page();
        page.setDownloadSuccess(false);
        return page;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRawText() {
        return rawText;
    }

    public Page setRawText(String rawText) {
        this.rawText = rawText;
        return this;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public boolean isDownloadSuccess() {
        return downloadSuccess;
    }

    public void setDownloadSuccess(boolean downloadSuccess) {
        this.downloadSuccess = downloadSuccess;
    }

}
