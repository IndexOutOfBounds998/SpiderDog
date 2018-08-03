package pre.cyy.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yang
 * @date 2018/7/26 15:12
 * @description HttpRequestBody post请求体
 */
public class HttpRequestBody {

    public static abstract class ContentType {

        public static final String JSON = "application/json";

        public static final String XML = "text/xml";

        public static final String FORM = "application/x-www-form-urlencoded";

        public static final String MULTIPART = "multipart/form-data";
    }

    private final byte[] body;

    private final String contentType;
    /**
     * @author yang
     * @date 2018/7/26 15:14
     * @description 转换给byte的编码
     */
    private final String encoding;

    public HttpRequestBody(byte[] body, String contentType, String encoding) {
        this.body = body;
        this.contentType = contentType;
        this.encoding = encoding;
    }

    public String getContentType() {
        return contentType;
    }

    public String getEncoding() {
        return encoding;
    }

    public static HttpRequestBody json(String json, String encoding) throws UnsupportedEncodingException {
        return new HttpRequestBody(json.getBytes(encoding), ContentType.JSON, encoding);
    }

    public static HttpRequestBody json(String json) throws UnsupportedEncodingException {
        return json(json, "utf-8");
    }

    public static HttpRequestBody json(Object obj) throws UnsupportedEncodingException {
        return json(JSON.toJSONString(obj), "utf-8");
    }

    public static HttpRequestBody json(JSONObject obj) throws UnsupportedEncodingException {
        return json((obj.toJSONString()), "utf-8");
    }

    public static HttpRequestBody xml(String xml, String encoding) throws UnsupportedEncodingException {
        return new HttpRequestBody(xml.getBytes(encoding), ContentType.XML, encoding);
    }

    public static HttpRequestBody custom(byte[] body, String contentType, String encoding) throws UnsupportedEncodingException {
        return new HttpRequestBody(body, contentType, encoding);
    }

    public static HttpRequestBody form(Map<String, Object> params, String encoding) throws UnsupportedEncodingException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        return new HttpRequestBody(URLEncodedUtils.format(nameValuePairs, encoding).getBytes(encoding), ContentType.FORM, encoding);
    }

    public byte[] getBody() {
        return body;
    }
}
