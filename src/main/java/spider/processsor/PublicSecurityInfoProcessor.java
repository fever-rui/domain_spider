package spider.processsor;

import org.apache.http.client.CookieStore;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class PublicSecurityInfoProcessor implements PageProcessor {
    public Site site = Site.me().setRetryTimes(3).setSleepTime(10000);

    public void process(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex("(http://www.beian.gov.cn/portal/registerSystemInfo)").all());
        String name =  page.getHtml().xpath("//div[@class='wzjb']/table/tbody/tr[1]/td[2]").toString();
        System.out.println("name:" + name);
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.out.println("spider end");
    }
}
