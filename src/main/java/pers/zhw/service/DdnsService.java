package pers.zhw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Integer saveLastedIp(DdnsIpLog ddnsIpLog) {
        return ddnsIpLogMapper.insertDdnsIpLog(ddnsIpLog);
    }

    public void deleteOneMonthAgo() {
        ddnsIpLogMapper.delOneMonteAgo();
    }
}
