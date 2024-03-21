package pers.zhw.service;

import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author houwei.zhao@ttpai.cn on 2023/4/6.
 */
@Slf4j
public class WanIpService {
    /**
     * 获取路由器wanIp地址
     */
    private static final String[] ip_http_url = new String[]{
            "http://www.ifconfig.me/ip","https://api.ip.sb/ip",
            "https://icanhazip.com","http://ip.3322.net","http://www.3322.org/dyndns/getip"
    };

    private static final Pattern ipv4_pattern = Pattern.compile( "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(5, TimeUnit.SECONDS).
            readTimeout(10, TimeUnit.SECONDS).build();

    /**
     * 获取ip
     * @param url
     * @return
     */
    private static String getNewestWanIp(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            String ipv4 = StringUtils.strip(StringUtils.strip(response.body().string(), ","), "\n");
            Matcher matcher = ipv4_pattern.matcher(ipv4);
            if (matcher.matches()) {
                log.error("请求ipv4地址是:{}", ipv4);
                return ipv4;
            }
        } catch (Exception e) {
            log.error("{}请求ip地址超时：", url, e);
            return null;
        }
        return null;
    }

    /**
     * 获取可用ip
     * @return
     */
    public static String getAvailableNewIp() {
        return Arrays.stream(ip_http_url).map(WanIpService::getNewestWanIp).filter(StringUtils::isNotBlank).findFirst().orElse(null);
    }
}
