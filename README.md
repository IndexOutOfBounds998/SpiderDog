**请求网络**

`java`
  
     
      HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
             //设置代理
             httpClientDownloader.setProxyProvider(new SimpleProxyProvider(null));
                Request request = new Request();
        request.setUrl("https://blog.csdn.net/xwnxwn/article/details/52510484");
        //get 
        request.setMethod(HttpConstant.Method.GET);
        Site site = Site.me();
        site.addHeader("Accept-Encoding", "gzip, deflate");
        site.setCharset("utf-8");
        site.setDomain("blog.csdn.net");
        site.setTimeOut(5000);
        site.addCookie("cookie", "11111111111");
        Page page = httpClientDownloader.download(request, site.toTask());
        System.out.println(page.getRawText());


        //post 
        Request requestPost = new Request();
        request.setUrl("https://blog.csdn.net/xwnxwn/article/details/52510484");
        requestPost.setMethod(HttpConstant.Method.POST);
        try {
            requestPost.setRequestBody(HttpRequestBody.json(null, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置站点
        Site sitepost = Site.me();
        site.addHeader("Accept-Encoding", "gzip, deflate");
        site.setCharset("utf-8");
        site.setDomain("blog.csdn.net");
        site.setTimeOut(5000);
        site.addCookie("cookie", "11111111111");
        Page pagePost = httpClientDownloader.download(request, sitepost.toTask());
        System.out.println(pagePost.getRawText());
        `java`
