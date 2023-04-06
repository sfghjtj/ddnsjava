package pers.zhw.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import pers.zhw.model.DdnsIpLog;

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
        String ip = WanIpService.getAvailableNewIp();
        if (StringUtils.isBlank(ip)) {
            return;
        }
        DdnsIpLog ipOld = ddnsService.findLastedIp();
        if (Objects.nonNull(ipOld) && Objects.equals(ip, ipOld.getIpAddr())) {
            return;
        }
        // 调用阿里SDK设置DDNS&入库存储
        ddnsService.invokeSdkAndSaveIp(ip);
    }

    /**
     * 定时更新dns
     */
    @Scheduled(fixedDelay = 30_000, initialDelay = 30_000)
    protected void refreshDdnsLog() {
        this.initIpLog();
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
