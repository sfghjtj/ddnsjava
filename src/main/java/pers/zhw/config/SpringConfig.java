package pers.zhw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @Author houwei.zhao@ttpai.cn on 2023/4/5.
 */
@ComponentScan("pers.zhw")
public class SpringConfig {

    @Bean
    public TaskScheduler taskExecutor() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(2);
        threadPoolTaskScheduler.setThreadNamePrefix("ddnsTaskExecutor");
        return threadPoolTaskScheduler;
    }
}
