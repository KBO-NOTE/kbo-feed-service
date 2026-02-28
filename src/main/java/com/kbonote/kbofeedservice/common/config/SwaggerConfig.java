package com.kbonote.kbofeedservice.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import java.util.ArrayList;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String HEADER_USER_ID = "X-User-ID";
    private static final String HEADER_USER_ROLE = "X-User-Role";
    private static final String HEALTH_PATH = "/api/v1/feeds/health";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("KBO Feed Service API")
                        .description("KBO feed service REST API documentation")
                        .version("v1")
                        .license(new License().name("Apache 2.0")));
    }

    @Bean
    public OpenApiCustomizer gatewayHeaderCustomizer() {
        return openApi -> {
            if (openApi.getPaths() == null) {
                return;
            }

            openApi.getPaths().forEach((path, pathItem) -> {
                if (!path.startsWith("/api/") || HEALTH_PATH.equals(path)) {
                    return;
                }

                pathItem.readOperations().forEach(operation -> {
                    ensureUserIdHeader(operation);
                    ensureUserRoleHeader(operation);
                });
            });
        };
    }

    private void ensureUserIdHeader(io.swagger.v3.oas.models.Operation operation) {
        if (hasHeader(operation, HEADER_USER_ID)) {
            return;
        }

        Parameter parameter = new Parameter()
                .in("header")
                .name(HEADER_USER_ID)
                .required(true)
                .description("Authenticated user id from API Gateway")
                .schema(new IntegerSchema().format("int64").example(1));

        addParameter(operation, parameter);
    }

    private void ensureUserRoleHeader(io.swagger.v3.oas.models.Operation operation) {
        if (hasHeader(operation, HEADER_USER_ROLE)) {
            return;
        }

        Parameter parameter = new Parameter()
                .in("header")
                .name(HEADER_USER_ROLE)
                .required(true)
                .description("Authenticated user role from API Gateway")
                .schema(new StringSchema().example("USER"));

        addParameter(operation, parameter);
    }

    private boolean hasHeader(io.swagger.v3.oas.models.Operation operation, String headerName) {
        return operation.getParameters() != null && operation.getParameters().stream()
                .anyMatch(param -> "header".equalsIgnoreCase(param.getIn()) && headerName.equalsIgnoreCase(param.getName()));
    }

    private void addParameter(io.swagger.v3.oas.models.Operation operation, Parameter parameter) {
        if (operation.getParameters() == null) {
            operation.setParameters(new ArrayList<>());
        }
        operation.getParameters().add(parameter);
    }
}
