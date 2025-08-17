package br.hendrew.goldenraspberryapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class SwaggerConfiguration {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getInfo());
    }

    private Info getInfo() {
        var version = getClass().getPackage().getImplementationVersion();
        return new Info()
                .title("Golden Raspberry Api")
                .version(Optional.ofNullable(version).orElse("debug"))
                .description("Golden Raspberry Api Documentation")
                .contact(new Contact()
                        .name("Hendrew Martins")
                        .url("https://github.com/HendrewMartins")
                );
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> openApi.getServers().clear();
    }

}
