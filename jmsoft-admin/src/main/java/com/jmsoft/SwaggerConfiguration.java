package com.jmsoft;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * api配置
 * @author Jack
 *
 */
public class SwaggerConfiguration {
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.jmsoft"))
				.paths(PathSelectors.any())
				.build();
	}
	
    //构建 api文档的详细信息函数	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				//页面标题
				.title("JM-Soft后台服务api")
				//创建人
				.contact("Jack")
				//版本号
				.version("1.0")
				//描述
				.description("API 描述")
				.build();
	}
}
