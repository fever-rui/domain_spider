package spider.service;

import image.ImageTool;
import image.OcrHelper;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import spider.common.HttpUtil;
import spider.processsor.PublicSecurityInfoProcessor;
import spider.processsor.PublicSecurityProcessor;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.utils.HttpConstant;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicSecuritySpiderService {

    private static final String UTF8 = "UTF-8";
    private static final String PUBLIC_SECURITY_INIT_URL = "http://www.beian.gov.cn/portal/registerSystemInfo";
    private static final String PUBLIC_SECURITY_CODE_URL = "http://www.beian.gov.cn/common/image.jsp?t=2";
    private static final String JSESSIONID= "JSESSIONID";
    private static final String WEBAGPT= "BIGipServerPOOL-WebAGPT";


    public void doSpider(String domain) {
        //1、获取cookie
        CookieStore cookieStore = HttpUtil.getCookieStore(new HashMap<String, String>(), UTF8, PUBLIC_SECURITY_INIT_URL);
        List<Cookie> cookies = cookieStore.getCookies();
        String sessionId = null;
        String webAGPT = null;
        for (int i = 0; i < cookies.size(); i++) {
            if (cookies.get(i).getName().equals(JSESSIONID)) {
                sessionId = cookies.get(i).getValue();
            }
            if (cookies.get(i).getName().equals(WEBAGPT)) {
                webAGPT = cookies.get(i).getValue();
            }
        }

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        //2、获取验证码
        String verCode = getVerCode(httpClient);
        int count = 0;
        while (verCode.length() != 4) {
            if (count > 10) {
                return;
            }
            count++;
            sleepWithTime(1500);
            verCode = getVerCode(httpClient);
        }
        //3、获取token
        String token = getToken(sessionId, webAGPT);

        //4、获取备案信息
        getCase(token, verCode, sessionId, webAGPT, domain);

    }

    public String getVerCode(CloseableHttpClient httpClient) {
        BufferedImage image = HttpUtil.getImageByResp(HttpUtil.doPost(httpClient,new HashMap<String, String>(), UTF8, PUBLIC_SECURITY_CODE_URL));
        return OcrHelper.ocrNum(ImageTool.grayAndBin(image));
    }

    public String getToken(String sessionId, String webAGPT) {
        PublicSecurityProcessor securityProcessor = new PublicSecurityProcessor();
        securityProcessor.site.addCookie(JSESSIONID, sessionId);
        securityProcessor.site.addCookie(WEBAGPT, webAGPT);
        Spider spider = Spider.create(securityProcessor);
        spider.addUrl(PUBLIC_SECURITY_INIT_URL)
                .thread(1).runAsync();

        sleepWithTime(2000);
        spider.stop();
        return securityProcessor.getToken();
    }

    public void getCase(String token, String verCode, String sessionId, String webAGPT, String domain) {
        StringBuilder stringBuilder = new StringBuilder(PUBLIC_SECURITY_INIT_URL);
        stringBuilder.append("?").append("flag=2&sdcx=1&token=").append(token).append("&domainname=").append(domain).append("&inputPassword=").append(verCode);

        Request request = new Request(stringBuilder.toString());
        //只有POST请求才可以添加附加参数
        request.setMethod(HttpConstant.Method.POST);
        //设置POST参数
        List<NameValuePair> nvs = new ArrayList<NameValuePair>();
        //转换为键值对数组
        NameValuePair[] values = nvs.toArray(new NameValuePair[]{});
        //将键值对数组添加到map中
        Map<String, Object> params = new HashMap<String, Object>();
        //key必须是：nameValuePair
        params.put("nameValuePair", values);
        //设置request参数
        request.setExtras(params);

        try {
            PublicSecurityInfoProcessor securityInfoProcessor = new PublicSecurityInfoProcessor();
            securityInfoProcessor.site.addCookie(JSESSIONID, sessionId);
            securityInfoProcessor.site.addCookie(WEBAGPT, webAGPT);
            Spider.create(securityInfoProcessor).addRequest(request).thread(1).runAsync();
            sleepWithTime(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sleepWithTime(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
