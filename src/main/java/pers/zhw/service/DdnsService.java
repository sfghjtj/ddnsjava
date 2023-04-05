package pers.zhw.service;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import pers.zhw.config.SpringConfig;
import pers.zhw.mapper.DdnsIpLogMapper;
import pers.zhw.model.DdnsIpLog;

/**
 * @Author houwei.zhao@ttpai.cn on 2023/4/5.
 */
@Service
public class DdnsService {

    @Autowired
    private DdnsIpLogMapper ddnsIpLogMapper;

    public DdnsIpLog findLastedIp() {
        return ddnsIpLogMapper.findDdnsIpLatest();
    }

}
