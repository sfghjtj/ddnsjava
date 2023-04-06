package pers.zhw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    /**
     * 调用阿里SDK&入库
     * @param ipv4
     */
    public void invokeSdkAndSaveIp(String ipv4) {
        DdnsIpLog ipLog = new DdnsIpLog().setIpAddr(ipv4).setCreateTime(new Date()).setModifyTime(new Date());

        this.saveLastedIp(ipLog);
    }

}
