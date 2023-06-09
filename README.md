## 通过阿里云SDK设置DDNS
- 环境: 
    * mysql 5.7+
    * jdk 8+ 
    * gradle 无需配置相关环境

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
ali_first_domain_name = yourFirstDomain(域名地址e.g. ddnslearn.cn)
ali_second_domain_prefix = yourSecondDomainPrefix(主机名e.g. www)
# 本地域名解析服务器过期时间，个人版默认600秒，可升级企业版，最快1s（不过个人版感觉足够，相对于第三方比如花生壳同样的ttl 阿里的稳定性更好）
ali_dns_ttl = 600

````

### 三、编译&执行
1. 编译 
- mac os/linux系统执行： ./gradlew clean build
- windows系统 执行：./gradlew.bat clean build
- 编译后生成的可执行jar包路径：build/libs/ddnsjava-1.0-SNAPSHOT.jar

2. 项目执行日志文件添加权限(windows系统自行修改log4j2.xml中日志目录)
- sudo chmod o+w /var/log

3. 命令行执行即可
- java -jar -Xms64m -Xmx128m -XX:MaxMetaspaceSize=128m build/libs/ddnsjava-1.0-SNAPSHOT.jar

### 四、docker容器执行
- Dockerfile镜像文件
````
# 添加java基础镜像
FROM java:8
# 复制jar包到容器
ADD ddnsjava-1.0-SNAPSHOT.jar /ddns.jar
# 指定日志挂载点
VOLUME /var/log
# 配置容器启动后执行命令
ENTRYPOINT java ${JAVA_OPTS} -jar /ddns.jar
````
- 构建镜像
````shell script
docker build -t ddns:1.0 .
````
- 运行容器
````shell script
docker run -d --cap-add=SYS_PTRACE -v /var/log:/var/log --restart=always -e TZ=Asia/Shanghai  ddns:1.0
````