package sningning.community.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author: Song Ningning
 * @date: 2020-08-27 22:44
 */
@Configuration
public class WkConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WkConfig.class);

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @PostConstruct
    public void init() {
        // 创建 Wk 图片目录
        File file = new File(wkImageStorage);
        if (!file.exists()) {
            file.mkdirs();
            LOGGER.info("创建wk图片目录：" + wkImageStorage);
        }
    }
}
