package pers.zhw.sdk.aliyun;

import com.aliyun.alidns20150109.models.DescribeDomainRecordsResponse;
import com.aliyun.alidns20150109.models.UpdateDomainRecordRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 阿里云解析文档api地址：https://next.api.aliyun.com/product/Alidns
 * @Author houwei.zhao@ttpai.cn on 2023/4/6.
 */
@Service
@Slf4j
public class AliDdnsSdkService {

    /**
     * 访问api accessKeyId
     */
    @Value("${ali_access_key_Id}")
    private  String accessKeyId;

    /**
     * 访问api accessKeySecret
     */
    @Value("${ali_access_key_secret}")
    private  String accessKeySecret;

    /**
     * 一级域名
     */
    @Value("${ali_first_domain_name}")
    private  String first_domain_name;

    /**
     * 二级域名前缀
     */
    @Value("${ali_second_domain_prefix}")
    private  String second_domain_prefix;

    /**
     * 本地域名解析服务器过期时间，个人版默认600秒，可升级企业版，最快1s
     */
    @Value("${ali_dns_ttl}")
    private  Long ttl;

    /**
     * 使用AK&SK初始化账号Client
     * @return Client
     * @throws Exception
     */
    public com.aliyun.alidns20150109.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "alidns.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.alidns20150109.Client(config);
    }

    /**
     * 获取域名解析ip地址
     * @return
     * @throws Exception
     */
    public String getDomainIp()  {

        try {
            com.aliyun.alidns20150109.Client client = this.createClient();
            com.aliyun.alidns20150109.models.DescribeDomainRecordsRequest describeDomainRecordsRequest = new com.aliyun.alidns20150109.models.DescribeDomainRecordsRequest();
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
            describeDomainRecordsRequest.setDomainName(first_domain_name).setRRKeyWord(second_domain_prefix);
            DescribeDomainRecordsResponse describeDomainRecordsResponse = client.describeDomainRecordsWithOptions(describeDomainRecordsRequest, runtime);
            return describeDomainRecordsResponse.body.domainRecords.record.get(0).value;
        } catch (Exception _error) {
            log.error("++++++++获取域名解析ip地址错误", _error);
        }
        return null;
    }

    /**
     * 获取域名解析记录id
     * @return
     * @throws Exception
     */
    public String getDomainRecordId()  {

        try {
            com.aliyun.alidns20150109.Client client = this.createClient();
            com.aliyun.alidns20150109.models.DescribeDomainRecordsRequest describeDomainRecordsRequest = new com.aliyun.alidns20150109.models.DescribeDomainRecordsRequest();
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
            describeDomainRecordsRequest.setDomainName(first_domain_name).setRRKeyWord(second_domain_prefix);
            DescribeDomainRecordsResponse describeDomainRecordsResponse = client.describeDomainRecordsWithOptions(describeDomainRecordsRequest, runtime);
            return describeDomainRecordsResponse.body.domainRecords.record.get(0).recordId;
        } catch (Exception _error) {
            log.error("++++++++获取域名解析记录id错误", _error);
        }
        return null;
    }

    /**
     * 设置域名解析的地址
     */
    public boolean updateDomainIp(String ipv4) {
        try {
            com.aliyun.alidns20150109.Client client = this.createClient();
            UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
            String recordId = getDomainRecordId();
            if (StringUtils.isBlank(recordId)) {
                return false;
            }
            request.setRR(second_domain_prefix).setRecordId(recordId).setType("A").setValue(ipv4).setTTL(ttl).setPriority(1L);
            client.updateDomainRecord(request);
            return true;
        } catch (Exception _error) {
            log.error("++++++++设置域名解析的地址", _error);
        }
        return false;
    }

    public static void main(String[] args) {
        new AliDdnsSdkService().updateDomainIp("192.168.2.3");
    }
}

