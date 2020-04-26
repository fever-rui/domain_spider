package spider.processsor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class IcpCaseProcessor implements PageProcessor {
    public Site site = Site.me().setRetryTimes(3).setSleepTime(10000);

    public void process(Page page) {
        String name =  page.getHtml().xpath("//body/table[0]/tbody/tr[1]/td[2]").toString();
        System.out.println("name:" + name);
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.out.println("spider end");
    }
}
