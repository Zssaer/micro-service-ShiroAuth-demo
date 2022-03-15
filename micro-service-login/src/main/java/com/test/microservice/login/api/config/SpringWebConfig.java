package com.test.microservice.login.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: TODO
 * @author: Zhaotianyi
 * @time: 2022/3/15 15:59
 */
@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
    //静态资源 映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // 解决swagger的相关文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}
