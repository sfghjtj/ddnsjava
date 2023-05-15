package pers.zhw.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import lombok.extern.slf4j.Slf4j;
import pers.zhw.config.SpringConfig;

/**
 * @Author houwei.zhao@ttpai.cn on 2023/4/5.
 */
@Slf4j
public class DdnsMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        applicationContext.registerShutdownHook();
    }
}
