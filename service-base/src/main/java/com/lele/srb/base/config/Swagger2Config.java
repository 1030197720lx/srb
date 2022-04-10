package com.lele.srb.base.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();
    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("尚融宝后台管理api")
                .description("本文描述xxxxxxx")
                .version("1.0")
                .contact(new Contact("lele", "xxxx", "xxx@163.com"))
                .build();
    }

    @Bean
    public Docket smsApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("smsApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/sms/.*")))
                .build();
    }

    private ApiInfo smsApiInfo() {
        return new ApiInfoBuilder()
                .title("尚融宝api")
                .description("本文描述xxxxxxx")
                .version("1.0")
                .contact(new Contact("lele", "xxxx", "xxx@163.com"))
                .build();
    }

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("尚融宝api")
                .description("本文描述xxxxxxx")
                .version("1.0")
                .contact(new Contact("lele", "xxxx", "xxx@163.com"))
                .build();
    }


}
