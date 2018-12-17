package com.jmsoft;

import org.springframework.context.annotation.Bean;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfiguration {
	
	/**
	 * 通过 createRestApi函数来构建一个DocketBean
	 * 函数名,可以随意命名,喜欢什么命名就什么命名
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//控制暴露出去的路径下的实例
				//如果某个接口不想暴露,可以使用以下注解
				//@ApiIgnore 这样,该接口就不会暴露在 swagger2 的页面下
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
