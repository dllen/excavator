package net.excavatortech.controller;

import net.excavatortech.biz.service.intf.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Spider {

    @Autowired
    private SpiderService spiderService;

    // http://www.funi.com/loupan/region_56_0_0_0_1
    @RequestMapping(value = "/spider")
    @ResponseBody
    public String spider(String url) {

        spiderService.crawl(url);

        return "spider start";
    }
}
