package com.revizia.challengebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//TODO fix this class to show swagger
//@Configuration
@EnableSwagger2
@EnableWebMvc
public class SpringFoxConfig  {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.revizia.challengebackend.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.getApiInfo());
    }


    public ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("Challenge Back-end Revizia! ")
                .description("This is a CRUD application using Spring Boot, to show all the knowledge about development using Java with Spring using current best practices!")
                .contact(this.getContact())
                .version("1.0")
                .build();
    }

    public Contact getContact(){
        return new Contact("Ricardo Soares", "https://github.com/ricardossbr", "riicardo.soares@gmail.com");
    }

}