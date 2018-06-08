
import com.alibaba.fastjson.JSON;
import pre.cyy.download.HttpClientDownloader;
import pre.cyy.proxy.SimpleProxyProvider;
import pre.cyy.request.HttpRequestBody;
import pre.cyy.request.Page;
import pre.cyy.request.Request;
import pre.cyy.request.Site;
import pre.cyy.utils.HttpConstant;

import java.io.UnsupportedEncodingException;

/**
 * @Author: yang
 * @Date: 2018/6/7.18:02
 * @Desc: test
 */
public class test {

    public static void main(String[] args) {

        HttpClientDownloader httpClientDownloader = HttpClientDownloader.getInstance();
        String url = "http://localhost/shanhumi/api/user/editUserInfo";
        buid buid = new buid();
        buid.setSessionid("10db0b69-7cca-4f0c-8efc-2a7f66bc32c3");
        buid.setNick_name("养狗");
        buid.setGender("0");
        buid.setUser_info("jhashdhshd");
        buid.setUser_avatar("https://avatars2.githubusercontent.com/u/19317441?s=400&u=d5806e8222b8eda1ee63c3155975cf0f361c8980&v=4");
        Request request = new Request();
        request.setUrl(url);
        request.setMethod(HttpConstant.Method.POST);
        try {
            request.setRequestBody(HttpRequestBody.json(JSON.toJSONString(buid), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Site site = Site.me();
        Page page = httpClientDownloader.download(request, site.toTask());
        System.out.println(page.getRawText());

    }


    private static class buid {
        String sessionid;
        String nick_name;
        String gender;
        String user_info;
        String user_avatar;

        public String getSessionid() {
            return sessionid;
        }

        public void setSessionid(String sessionid) {
            this.sessionid = sessionid;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getUser_info() {
            return user_info;
        }

        public void setUser_info(String user_info) {
            this.user_info = user_info;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }
    }
}
