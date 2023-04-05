package pers.zhw;

import com.alibaba.fastjson.JSON;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import pers.zhw.config.SpringConfig;
import pers.zhw.model.DdnsIpLog;
import pers.zhw.service.DdnsService;

/**
 * @Author houwei.zhao@ttpai.cn on 2023/4/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes = {SpringConfig.class})
public class DdnsServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(DdnsServiceTest.class);

    @Autowired
    private DdnsService ddnsService;

    @Test
    public void findDao() {
        DdnsIpLog lastedIp = ddnsService.findLastedIp();
        logger.error(JSON.toJSONString(lastedIp));
    }

    @Test
    public void saveDao() {
        DdnsIpLog ipLog = new DdnsIpLog();
        ipLog.setIpAddr("192.168.2.22").setCreateTime(new Date()).setModifyTime(new Date());
        Integer ipId = ddnsService.saveLastedIp(ipLog);
        logger.error(JSON.toJSONString(ipLog));

    }

}
