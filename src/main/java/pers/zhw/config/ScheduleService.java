package pers.zhw.config;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import pers.zhw.service.DdnsService;

/**
 * @Author houwei.zhao@ttpai.cn on 2023/4/5.
 */
@Component
@Slf4j
@EnableScheduling
public class ScheduleService {
    @Autowired
    private DdnsService ddnsService;

    @PostConstruct
    public void initIpLog() {

        System.out.println(JSON.toJSONString(ddnsService.findLastedIp()));
    }

    /**
     * 定时更新dns
     */
    @Async
    @Scheduled(fixedDelay = 30_000, initialDelay = 30_000)
    protected void refreshDdnsLog() {
    }

    /**
     * 定时删除ip
     */
    @Scheduled(fixedDelay = 24 * 60 * 60 * 1_000, initialDelay = 60_000)
    protected void refreshDelDdnsLog() {
        log.error("删除一个月之前数据start.....");
        ddnsService.deleteOneMonthAgo();
        log.error("删除一个月之前数据end.....");
    }
}
