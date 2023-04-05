package pers.zhw.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author houwei.zhao@ttpai.cn on 2023/4/5.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DdnsIpLog {

    /**
     * ipv4地址
     */
    private String ipAddr;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 记录修改时间
     */
    private Date modifyTime;
}
