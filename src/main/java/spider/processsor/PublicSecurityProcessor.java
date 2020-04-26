package spider.processsor;

import org.apache.http.client.CookieStore;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class PublicSecurityProcessor implements PageProcessor {
    public Site site = Site.me().setRetryTimes(3).setSleepTime(10000);

    private String token;

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(http://www.beian.gov\\.cn/\\w+/\\w+)").all());
        page.putField("name", page.getHtml().xpath("//head/title/text()").toString());
        token = page.getHtml().xpath("//head/script[2]").toString().split("'")[1];
        page.putField("token", page.getHtml().xpath("//head/script[2]").toString().split("'")[1]);
    }

    public Site getSite() {
        return site;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
