package spider.controller;

import spider.service.IcpSpiderService;

public class IcpCaseController {
    public static void doSpider() {
        IcpSpiderService icpSpiderService = new IcpSpiderService();
        icpSpiderService.doSpider("baidu.com", "71235837");
    }

    public static void main(String[] args) {
        doSpider();
    }
}
