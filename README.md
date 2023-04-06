## 通过阿里云SDK设置DDNS

### 一、mysql 5.7 
````mysql
create database ddns;
use ddns;
CREATE TABLE `DDNS_IP_LOG` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `IP_ADDR` varchar(30) NOT NULL DEFAULT '' COMMENT 'IPV4地址',
  `CREATE_TIME` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `MODIFY_TIME` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  KEY `IDX_CREATE_TIME`(`CREATE_TIME`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='DNS绑定的路由器wanIp';
````

### 二、配置文件ddns.properties
````properties
## jdbc config
jdbc_driver=com.mysql.jdbc.Driver
jdbc_url=jdbc:mysql://{yourIp}:3306/ddns?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
jdbc_user={your login user}
jdbc_password={your login pswd}
jdbc_initSize=5
jdbc_maxSize=100

## aliyun ddns openapi config
ali_access_key_Id =yourKeyId
ali_access_key_secret = yourKeySecret
ali_first_domain_name = yourFirstDomain(e.g. ddnslearn.cn)
ali_second_domain_prefix = yourSecondDomainPrefix(e.g. www)
# 本地域名解析服务器过期时间，个人版默认600秒，可升级企业版，最快1s
ali_dns_ttl = 600

````
### 三、编译&执行
- ./gradlew clean build
- sudo chmod o+w /var/log
- java -jar -Xms64m -Xmx128m -XX:MaxMetaspaceSize=128m /Users/sfghjtj/Documents/java/personal_pro/ddnsjava/build/libs/ddnsjava-1.0-SNAPSHOT.jar 