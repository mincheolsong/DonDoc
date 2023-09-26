package com.dondoc.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * http://localhost:9191/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Bean
    public Docket swaggerAPI(){ // Docket : Swagger 설정의 핵심이 되는 Bean
        ParameterBuilder parameterBuilder = new ParameterBuilder();

        List<Parameter> parameters = new ArrayList<>();

        parameterBuilder.name("Authorization")
                .description("accessToken")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .defaultValue("Bearer ")
                .build();

        parameters.add(parameterBuilder.build());


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()) // Swagger UI로 노출할 정보
                .globalOperationParameters(parameters)
                .useDefaultResponseMessages(true) // 기본 응답 코드(200, 401, 403, 404) 표시 여부
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dondoc.backend")) //swagger탐색 대상 패키지
                .paths(PathSelectors.ant("/api/**")) // apis에 있는 API중 특정 path를 선택
                .build();

    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("DonDoc Swagger")
                .build();
    }

}