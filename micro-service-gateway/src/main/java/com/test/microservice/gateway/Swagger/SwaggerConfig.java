package com.test.microservice.gateway.Swagger;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 网关配置Swagger
 * 将其微服务中的API模块Swagger文档组合在一起
 * @author: Zhaotianyi
 * @time: 2022/3/15 15:19
 */
@Component
@Primary
public class SwaggerConfig implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {

        List resources = new ArrayList<>();

        //login-component --->  添加登录模块
        resources.add(swaggerResource("login-component", "/login-api/v3/api-docs", "2.0"));

        return resources;
    }

    private Object swaggerResource(String name, String location, String version) {

        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
