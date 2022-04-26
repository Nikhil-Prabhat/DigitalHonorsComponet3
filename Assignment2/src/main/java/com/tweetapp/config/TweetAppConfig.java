package com.tweetapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class TweetAppConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Tweet App").apiInfo(apiInfo()).select()
				.paths(regex("/api/v1.0/tweets.*")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("TweetApp Service")
				.description("Documentation Generated Using SWAGGER2 for TweetApp Service").version("1.0").build();
	}

}
