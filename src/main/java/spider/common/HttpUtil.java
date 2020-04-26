package spider.common;

import image.ImageTool;
import image.OcrHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import spider.processsor.PublicSecurityInfoProcessor;
import spider.processsor.PublicSecurityProcessor;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HttpUtil {
    // 同步锁
    private final Lock mLock = new ReentrantLock();
    // 队列满时的条件
    private Condition tokenNotNull = mLock.newCondition();

    public static CookieStore getCookieStore(Map<String, String> map, String charset, String url) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        CookieStore cookieStore = null;
        HttpResponse resp = null;
        try {
            cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (!list.isEmpty()) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            resp = httpClient.execute(httpPost);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cookieStore;
    }

    public static CookieStore getCookieStore2(Map<String, String> map, String charset, String url) {
        CookieStore cookieStore= null;
        HttpPost httpPost = null;
        CloseableHttpClient httpClient= null;
        HttpResponse resp = null;
        try {
            cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost = new HttpPost(url);
            httpPost.setHeader("cookie", "__jsluid=f6c384bc14fdf2a671cf1b4cea1bacd4; __jsl_clearance=1538038693.131|0|bH%2BV%2BW38%2BzqJfmbrGzeQb8emaTQ%3D");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (!list.isEmpty()) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            resp = httpClient.execute(httpPost);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cookieStore;
    }

    public static HttpResponse doPost(CloseableHttpClient httpClient, Map<String, String> map, String charset, String url) {
        HttpResponse resp = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (!list.isEmpty()) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            resp = httpClient.execute(httpPost);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resp;
    }

    public static HttpResponse doPost(CloseableHttpClient httpClient,HttpPost httpPost, Map<String, String> map, String charset, String url) {
        HttpResponse resp = null;
        try {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (!list.isEmpty()) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            resp = httpClient.execute(httpPost);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resp;
    }

    public static HttpResponse doPost2(CloseableHttpClient httpClient, Map<String, String> map, String charset, String url) {
        HttpResponse resp = null;
//        httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate");
            httpPost.addHeader("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
            httpPost.addHeader("Connection","keep-alive");
            httpPost.addHeader("Cookie","__jsluid=f3e2db0a7bc47a0dbd7104d2c24c675a; __jsl_clearance=1538065389.693|0|chf85gacepTHI4mdfzrBuoHYnt4%3D");
            httpPost.addHeader("Host","www.miitbeian.gov.cn");
            httpPost.addHeader("Referer","http://www.miitbeian.gov.cn/publish/query/indexFirst.action");
            httpPost.addHeader("Upgrade-Insecure-Requests","1");
            httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (!list.isEmpty()) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            resp = httpClient.execute(httpPost);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resp;
    }



    public static BufferedImage getImageByResp(HttpResponse resp) {
        BufferedImage image = null;
        if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
            try {
                HttpEntity entity = resp.getEntity();
                InputStream in = entity.getContent();
                byte[] b = new byte[1024];
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                int length = 0;
                while ((length = in.read(b)) != -1) {
                    bout.write(b, 0, length);
                }
                ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
                image = ImageIO.read(bin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static void doPost(Map<String, String> map, String charset) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        CookieStore cookieStore = null;
        String ocrResult = null;
        try {
            cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost = new HttpPost("http://www.beian.gov.cn/portal/registerSystemInfo");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            httpClient.execute(httpPost);
            httpPost = new HttpPost("http://www.beian.gov.cn/common/image.jsp?t=2");
            HttpResponse resp = httpClient.execute(httpPost);
            if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
                HttpEntity entity = resp.getEntity();
                InputStream in = entity.getContent();
                byte[] b = new byte[1024];
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                int length = 0;
                while ((length = in.read(b)) != -1) {
                    bout.write(b, 0, length);
                }
                ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
                BufferedImage image = ImageIO.read(bin);
                image = ImageTool.grayImage(image);
                image = ImageTool.binarzing(image, 127);
                ocrResult = OcrHelper.ocrNum(image);

            }

            String JSESSIONID = null;
            String cookie_user = null;
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                if (cookies.get(i).getName().equals("JSESSIONID")) {
                    JSESSIONID = cookies.get(i).getValue();
                }
                if (cookies.get(i).getName().equals("BIGipServerPOOL-WebAGPT")) {
                    cookie_user = cookies.get(i).getValue();
                }
            }


            PublicSecurityProcessor securityProcessor = new PublicSecurityProcessor();
//            securityProcessor.site.addCookie("JSESSIONID", JSESSIONID);
//            securityProcessor.site.addCookie("BIGipServerPOOL-WebAGPT", cookie_user);
            Spider spider = Spider.create(securityProcessor);
            spider.addUrl("http://www.beian.gov.cn/portal/registerSystemInfo")
                    .thread(1).runAsync();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spider.stop();
            Map<String, String> param = new HashMap<String, String>();
            param.put("flag", Integer.toString(2));
            param.put("sdcx", Integer.toString(1));
            param.put("token", securityProcessor.getToken());
            param.put("domainname", "duoyi.com");
            param.put("inputPassword", ocrResult);
            StringBuilder stringBuilder = new StringBuilder("http://www.beian.gov.cn/portal/registerSystemInfo");
            stringBuilder.append("?").append("flag=2&sdcx=1&token=").append(securityProcessor.getToken()).append("&domainname=").append("duoyi.com&inputPassword=").append(ocrResult);
            Request request = new Request(stringBuilder.toString());
            //只有POST请求才可以添加附加参数
            request.setMethod(HttpConstant.Method.POST);

            //设置POST参数
            List<NameValuePair> nvs = new ArrayList<NameValuePair>();
//            nvs.add(new BasicNameValuePair("flag", Integer.toString(2)));
//            nvs.add(new BasicNameValuePair("sdcx", Integer.toString(1)));
//            nvs.add(new BasicNameValuePair("token", securityProcessor.token));
//            nvs.add(new BasicNameValuePair("domainname", "duoyi.com"));
//            nvs.add(new BasicNameValuePair("inputPassword", ocrResult));

            //转换为键值对数组
            NameValuePair[] values = nvs.toArray(new NameValuePair[]{});

            //将键值对数组添加到map中
            Map<String, Object> params = new HashMap<String, Object>();
            //key必须是：nameValuePair
            params.put("nameValuePair", values);

            //设置request参数
            request.setExtras(params);

            // 开始执行
            try {
                PublicSecurityInfoProcessor securityInfoProcessor = new PublicSecurityInfoProcessor();
                securityInfoProcessor.site.addCookie("JSESSIONID", JSESSIONID);
                securityInfoProcessor.site.addCookie("BIGipServerPOOL-WebAGPT", cookie_user);
                Spider.create(securityInfoProcessor).addRequest(request).thread(1).run();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
