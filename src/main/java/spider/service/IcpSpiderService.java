package spider.service;

import image.ImageTool;
import image.OcrHelper;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import spider.common.HttpUtil;
import spider.processsor.IcpCaseProcessor;
import spider.processsor.PublicSecurityInfoProcessor;
import spider.processsor.PublicSecurityProcessor;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class IcpSpiderService {
    private static final String UTF8 = "UTF-8";
    private static final String ICP_INIT_URL = "http://www.miitbeian.gov.cn/publish/query/indexFirst.action";
    private static final String ICP_CODE_URL = "http://www.miitbeian.gov.cn/getDetailVerifyCode?7";
    private static final String ICP_CASE_URL = "http://www.miitbeian.gov.cn/icp/publish/query/icpMemoInfo_login.action";
    private static final String JSESSIONID = "JSESSIONID";
//    private static final String JSLUID = "f3e2db0a7bc47a0dbd7104d2c24c675a";
    private static final String JSLUID_NAME = "__jsluid";
//    private static final String JSLCLEARANCE = "1538062728.838|0|p3plNAI5CKzDcXUbqYUWkAUUGFM%3D";
    private static final String JSLCLEARANCE_NAME = "__jsl_clearance";
    private static final int KEYX = 3;
    private static final int KEYY = 4;

    public void doSpider(String domain, String icpDomainId) {
        //1、获取cookie
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpPost httpPost = new HttpPost(ICP_INIT_URL);

        HttpResponse response =  HttpUtil.doPost(httpClient, new HashMap<String, String>(), UTF8, ICP_INIT_URL);

        String content;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            int num = 0;
            while ((line = in.readLine()) != null) {
                if (num != 0) {
                    sb.append(NL);
                    sb.append(line);
                } else {
                    sb.append(line);
                    num = 1;
                }
            }
            in.close();
            content = sb.toString().trim();

//            InputStream in=response.getEntity().getContent();
//            int count = 0;
//            while (count == 0) {
//                count = Integer.parseInt(""+response.getEntity().getContentLength());//in.available();
//            }
//            byte[] bytes = new byte[count];
//            int readCount = 0; // 已经成功读取的字节的个数
//            while (readCount <= count) {
//                if(readCount == count)break;
//                readCount += in.read(bytes, readCount, count - readCount);
//            }
//
//            //转换成字符串
//            content= new String(bytes, 0, readCount, "UTF-8"); // convert to string using bytes
//            content="<script>var x=\"if@function@setTimeout@@try@@parseInt@cookie@0xFF@charAt@@document@fromCharCode@kq@attachEvent@var@RT@2@Zg@18@new@9@@search@join@@36@chars@Path@@@D@Thu@div@1@@@match@eval@__jsl_clearance@wmkQ@@@@return@@W@location@else@pathname@createElement@JD@@@replace@catch@reverse@606@@window@charCodeAt@@Expires@Sep@@0xEDB88320@@toLowerCase@20@firstChild@while@@DOMContentLoaded@innerHTML@https@href@@12@@@37@@captcha@split@@RegExp@GMT@@@@toString@KC@headless@0@3@String@@addEventListener@@@@rOm9XFMtA3QKV7nYsPGT4lifyWwkq5vcjH2IdxUoCbhERLaz81DNB6@onreadystatechange@length@@@@@21@Array@@@substr@1500@a@d@f@@1537442497@g@8@challenge@for@false@@JgSe0upZ@e@\".replace(/@*$/,\"\").split(\"@\"),y=\"g 10=2(){3('1i.2g=1i.1k+1i.o.1p(/[\\\\?|&]2n-42/,\\\\'\\\\')',3o);c.8='1a=3t.1s|34|'+(2(){g 10=[[(-~!{}<<-~!{})-~!{}-~{}-~(+[])-~(+[])],(-~[]+(-~{}+[(-~!{}<<-~!{})])/[(-~!{}<<-~!{})]+[]+[]),(-~!{}+[]+[])+[i],[(-~!{}<<(-~(+[])+[(-~!{}<<-~!{})]>>(-~!{}<<-~!{})))],(-~!{}+[]+[])+[35],(-~!{}+[]+[])+(-~!{}+[]+[]),[i],(-~!{}+[]+[])+(-~!{}-~(+[])-~(+[])-~(+[])-~(+[])+[]),(-~!{}+[]+[]),(-~!{}+[]+[])+[-~(35)],(-~!{}-~(+[])-~(+[])-~(+[])-~(+[])+[]),[35],(m+[]),(-~!{}+[]+[])+[(-~!{}<<(-~(+[])+[(-~!{}<<-~!{})]>>(-~!{}<<-~!{})))],(-~!{}+[]+[])+(-~[]+(-~{}+[(-~!{}<<-~!{})])/[(-~!{}<<-~!{})]+[]+[]),((+[])+[[]][34]),(-~!{}+[]+[])+((+[])+[[]][34]),[-~(35)],(-~!{}+[]+[])+[(-~!{}<<-~!{})-~!{}-~{}-~(+[])-~(+[])]],n=3k(10.3e);43(g 3f=34;3f<10.3e;3f++){n[10[3f]]=['1b',[(-~!{}<<(-~(+[])+[(-~!{}<<-~!{})]>>(-~!{}<<-~!{})))]+([]%!!20.33+[]).a(-~!{}),'1h','32',([i]/(+[])+[]+[]).a((-~(+[])-~(+[])<<-~{}))+(-~!{}-~(+[])-~(+[])-~(+[])-~(+[])+[]),((+[])+[[]][34]),'e',({}+[[]][34]).a(-~!{}),(!''+[]+[[]][34]).a(-~[])+[(-~!{}<<-~!{})-~!{}-~{}-~(+[])-~(+[])],'1m',[(-~[-~(+!+[])])/(+[])+[]+[[]][34]][34].a(~~''),[{}+[]+[]][34].a(i),[{}+[]+[[]][34]][34].a((-~{}|(-~!{}<<-~!{}))),'12',[35],'40','j','h','%'][3f]};1f n.p('')})()+';23=13, 29-24-k 2i:3j:2l 2r;t=/;'};1((2(){5{1f !!20.38;}1q(47){1f 44;}})()){c.38('2d',10,44)}1j{c.f('3d',10)}\",f=function(x,y){var a=0,b=0,c=0;x=x.split(\"\");y=y||99;while((a=x.shift())&&(b=a.charCodeAt(0)-77.5))c=(Math.abs(b)<13?(b+48.5):parseInt(a,36))+y*c;return c},z=f(y.match(/\\w/g).sort(function(x,y){return f(x)-f(y)}).pop());while(z++)try{eval(y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)}));break}catch(_){}</script>";
            String resHtml = "function getClearance(){" + content+"};";
            resHtml = resHtml.replace("</script>", "");
            resHtml = resHtml.replace("eval", "return");
            resHtml = resHtml.replace("<script>", "");
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            engine.eval(resHtml);
            Invocable invocable = (Invocable) engine;
            String resJs = (String) invocable.invokeFunction("getClearance");
            //一级解密结果
            System.out.println(resJs);

            String overJs="function getClearance2(){ var a" + resJs.split("document.cookie")[1].split("Path=/;'")[0]+"Path=/;';return a;};";
            overJs=overJs.replace("window.headless", "''");
            System.out.println(overJs);
            engine.eval("function getClearance2(){ var a='__jsl_clearance=1538074688.13|0|'+(function(){var _3n=[function(_23){return('String.fromCharCode('+_23+')')},function(_23){for(var _3n=0;_3n<_23.length;_3n++){_23[_3n]=parseInt(_23[_3n]).toString(36)};return _23.join('')}],_23=[[(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[])+[-~{}+[-~-~{}]*(((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))],(5+[[]][0])+(~~!/!/+[]+[]),[-~{}+[-~-~{}]*(((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))]+(~~!/!/+[]+[]),[-~{}+[-~-~{}]*(((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))]+(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[])],[[-~[]]+(5+[[]][0]),[-~[]]+(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[]),[-~[]]+[((+!!!window['_p'+'hantom'])<<((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))],(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[])+(5+[[]][0])],[((-~~~''<<2)+[]+[])+[((+!!!window['_p'+'hantom'])<<((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))],[((+!!!window['_p'+'hantom'])<<((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))]+(5+[[]][0])],[(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[])+[-~[]]],[[6]+[6],[((+!!!window['_p'+'hantom'])<<((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))]+(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[])],[[-~[]]+((-~~~''<<2)+[]+[])],[[6]+[6]],[[-~[]]+(5+[[]][0])],[[-~{}+[-~-~{}]*(((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))]+[-~{}+[-~-~{}]*(((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))],(5+[[]][0])+[6]],[(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[])+[-~[]]],[(5+[[]][0])+(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[])],[(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[])+[(-~[]<<-~[])]],[[6]+[((+!!!window['_p'+'hantom'])<<((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))],[6]+(5+[[]][0]),(5+[[]][0])+(~~!/!/+[]+[])],[(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[])+[(-~[]<<-~[])]],[(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[])+[-~{}+[-~-~{}]*(((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))],(5+[[]][0])+(~~!/!/+[]+[]),[6]+[6],[((+!!!window['_p'+'hantom'])<<((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))]+[(-~[]<<-~[])],[((+!!!window['_p'+'hantom'])<<((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))]+[((+!!!window['_p'+'hantom'])<<((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))],[((+!!!window['_p'+'hantom'])<<((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))]+(-~~~''+[~~{}]-(-~~~'')+[[]][0]),(((+!!!window['_p'+'hantom'])|(-~[]<<-~[]))+[])+[-~{}+[-~-~{}]*(((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))],(5+[[]][0])+[-~[]],[6]+[((+!!!window['_p'+'hantom'])<<((+!!!window['_p'+'hantom'])|(-~[]<<-~[])))]]];for(var _24=0;_24<_23.length;_24++){_23[_24]=_3n.reverse()[[-~[]]](_23[_24])};return _23.join('')})()+';Expires=Thu, 27-Sep-18 19:58:08 GMT;Path=/;';return a;};");
            Invocable invocable2 = (Invocable) engine;
            String over = (String) invocable2.invokeFunction("getClearance2");
            System.out.println(over);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        Header[] headers = response.getHeaders("set-cookie");
        List<Cookie> cookies = cookieStore.getCookies();
        String sessionId = null;
        String jslClearance = null;
        String jsluid = null;
        for (int i = 0; i < cookies.size(); i++) {
            if (cookies.get(i).getName().equals(JSESSIONID)) {
                sessionId = cookies.get(i).getValue();
            }
            String name = cookies.get(i).getName();
            if (name.equals(JSLUID_NAME)) {
                jsluid = cookies.get(i).getValue();
            }
            if (cookies.get(i).getName().equals(JSLCLEARANCE_NAME)) {
                jslClearance = cookies.get(i).getValue();
            }
        }
        httpPost.addHeader("Cookie","__jsluid=" + jsluid);
        httpPost.addHeader("Referer","http://www.miitbeian.gov.cn/publish/query/indexFirst.action");
        httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        response = HttpUtil.doPost(httpClient, httpPost, new HashMap<String, String>(), UTF8, ICP_INIT_URL);


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


        //4、获取备案信息
        getCase(verCode, sessionId, jslClearance, jsluid, domain, icpDomainId);

    }

    public String getVerCode(CloseableHttpClient httpClient) {
        BufferedImage image = HttpUtil.getImageByResp(HttpUtil.doPost(httpClient,new HashMap<String, String>(), UTF8, ICP_CODE_URL));
        return OcrHelper.ocrNum(ImageTool.grayBinClearLine(image, KEYX, KEYY));
    }

    public void getCase(String verCode, String sessionId, String jslClearance, String jsluid, String domain, String icpDomainId) {
        StringBuilder stringBuilder = new StringBuilder(ICP_CASE_URL);
        stringBuilder.append("?").append("certType=-1&bindFlag=0&mainUnitNature=-1").append("&siteDomain=").append(domain).append("&verifyCode=").append(verCode).append("&id=").append(icpDomainId);

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
            IcpCaseProcessor icpCaseProcessor = new IcpCaseProcessor();
//            icpCaseProcessor.site.addCookie(JSESSIONID, sessionId);
            icpCaseProcessor.site.addCookie(JSLUID_NAME, jsluid);
//            icpCaseProcessor.site.addCookie(JSLCLEARANCE, jslClearance);
            Spider.create(icpCaseProcessor).addRequest(request).thread(1).runAsync();
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
