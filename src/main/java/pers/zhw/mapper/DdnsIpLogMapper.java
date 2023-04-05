package pers.zhw.mapper;

import pers.zhw.model.DdnsIpLog;

/**
 * @Author houwei.zhao@ttpai.cn on 2023/4/5.
 */
public interface DdnsIpLogMapper {

    DdnsIpLog findDdnsIpLatest();

   // Long insertDdnsIpLog(DdnsIpLog entity);
}
