package sningning.community.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sningning.community.controller.interceptor.AlphaInterceptor;
import sningning.community.controller.interceptor.LoginTicketInterceptor;

/**
 * @Author: Song Ningning
 * @Date: 2020-08-04 19:25
 */

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private AlphaInterceptor alphaInterceptor;

    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String cssPath = "/**/*.css";
        String pngPath = "/**/*.png";
        String jpgPath = "/**/*.jpg";
        String jpegPath = "/**/*.jpeg";
        String registerPath = "/register";
        String loginPath = "/login";

        registry.addInterceptor(alphaInterceptor)
                .excludePathPatterns(cssPath, pngPath, jpgPath, jpegPath)
                .addPathPatterns(registerPath, loginPath);

        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns(cssPath, pngPath, jpgPath, jpegPath);
    }
}
