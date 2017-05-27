package net.excavatortech.biz.service.impl;

import net.excavatortech.biz.service.intf.SpiderService;
import net.excavatortech.biz.utils.AsyncUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

import javax.annotation.Resource;


@Service("spiderService")
public class SpiderServiceImpl implements SpiderService {

    public static Logger logger = LoggerFactory.getLogger(SpiderServiceImpl.class);

    @Value("${logging.path}")
    private String cacheQueuePath;

    @Resource(name = "funiSpiderProcessor")
    private PageProcessor pageProcessor;

    @Resource(name = "elasticSearchPipeline")
    private Pipeline elasticSearchPipeline;

    @Override
    public void crawl(String url) {
        final String startUrl = url;
        AsyncUtil.execute(() -> {
            Spider.create(pageProcessor)
                    // 从url开始抓
                    .addUrl(startUrl)
                    // 设置Scheduler，使用File来管理URL队列, 可以去重爬取
                    .setScheduler(new FileCacheQueueScheduler(cacheQueuePath + "/queue"))
                    // 设置Pipeline，将结果输出到elasticSearch
                    .addPipeline(elasticSearchPipeline)
                    // 开启5个线程同时执行
                    .thread(5)
                    // 启动爬虫
                    .run();
            logger.info("spider run success!");
        });
        logger.info("spider start!");
    }
}
