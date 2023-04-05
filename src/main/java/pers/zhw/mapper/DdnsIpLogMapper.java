package pers.zhw.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import pers.zhw.model.DdnsIpLog;

/**
 * @Author houwei.zhao@ttpai.cn on 2023/4/5.
 */
@Mapper
public interface DdnsIpLogMapper {

    @Select("select ip_addr,create_time,modify_time from DDNS_IP_LOG order by Id desc  limit 1")
    @ResultMap("ipLog")
    DdnsIpLog findDdnsIpLatest();

   // Long insertDdnsIpLog(DdnsIpLog entity);
}
