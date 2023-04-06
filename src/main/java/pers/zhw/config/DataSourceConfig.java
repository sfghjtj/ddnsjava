package pers.zhw.config;

import com.alibaba.druid.pool.DruidDataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author houwei.zhao@ttpai.cn on 2023/4/5.
 */
@Configuration
@PropertySource("classpath:ddns.properties")
public class DataSourceConfig {

    @Bean(name = "druidDataSource")
    public DruidDataSource druidDataSource(@Value("${jdbc_driver}") String drive, @Value("${jdbc_url}") String url,
                                           @Value("${jdbc_user}") String userName, @Value("${jdbc_password}") String pwd,
                                           @Value("${jdbc_initSize}") int initSize, @Value("${jdbc_maxSize}") int maxSize) {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(drive);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(pwd);
        dataSource.setInitialSize(initSize);
        dataSource.setMaxActive(maxSize);
        return dataSource;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        msc.setBasePackage("pers.zhw.mapper");
        return msc;
    }

    /**
     * 配置mybatis
     * @param druidDataSource
     * @return
     */
    @Bean(name = "sqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Autowired @Qualifier("druidDataSource") DruidDataSource druidDataSource) {
        SqlSessionFactoryBean ssb = new SqlSessionFactoryBean();
        ssb.setDataSource(druidDataSource);
        ssb.setTypeAliasesPackage("pers.zhw.model");
        return ssb;
    }

}
