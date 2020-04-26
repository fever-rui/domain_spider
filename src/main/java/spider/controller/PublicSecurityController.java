package spider.controller;

import spider.service.PublicSecuritySpiderService;

public class PublicSecurityController {
    public static void doSpider() {
        PublicSecuritySpiderService securitySpiderService = new PublicSecuritySpiderService();
        securitySpiderService.doSpider("baidu .com");
    }

    public static void main(String[] args) {
        doSpider();
    }
}
