package net.excavatortech.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import net.excavatortech.biz.service.intf.SpiderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时器 elastic-job
 */
@Component
public class SpiderElasticJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(SpiderElasticJob.class);

    @Autowired
    private SpiderService spiderService;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            spiderService.crawl("http://www.funi.com/loupan/region_56_0_0_0_1");
            logger.info("spider elastic job execute.");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
