package kr.co.fittnerserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi authGroupApi() {
        return GroupedOpenApi.builder()
                .group("1")
                .displayName("인증")
                .pathsToMatch("/api/v1/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userGroupApi() {
        return GroupedOpenApi.builder()
                .group("2")
                .displayName("트레이너")
                .pathsToMatch("/api/v1/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminGroupApi() {
        return GroupedOpenApi.builder()
                .group("3")
                .displayName("대표")
                .pathsToMatch("/api/v1/admin/**")
                .build();
    }

    @Bean
    public GroupedOpenApi commonGroupApi() {
        return GroupedOpenApi.builder()
                .group("4")
                .displayName("공통")
                .pathsToMatch("/api/v1/common/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fittner API")
                        .version("1.0.0")
                        .description("Fittner API with Spring Boot"));
    }
}
