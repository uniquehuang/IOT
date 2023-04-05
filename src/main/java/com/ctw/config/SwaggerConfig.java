package com.ctw.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 //开启Swagger2
public class SwaggerConfig {

    //是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.ctw")) //你的项目基础包名
//                .paths(PathSelectors.any())
//                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("物联网云平台开发")
                .contact(new Contact("陈廷伟", "", "787572600@qq.com"))
                .description("物联网云平台设计与开发，Swagger测试接口")
                .version("1.0") //版本
                .build();
    }

}