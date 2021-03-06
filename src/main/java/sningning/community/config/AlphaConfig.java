package sningning.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @author: Song Ningning
 * @date: 2020-04-21 0:21
 */
@Configuration
public class AlphaConfig {

    @Bean
    public SimpleDateFormat simpleDateFormat() {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }



}
