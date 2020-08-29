package sningning.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author Song Ningning
 */
@SpringBootApplication
public class CommunityApplication {

    @PostConstruct
    public void init() {
        // 解决 Netty 启动冲突的问题
        // Netty4Utils 类中 setAvailableProcessors 方法
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
