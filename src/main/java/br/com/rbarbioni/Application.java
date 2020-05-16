package br.com.rbarbioni;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
    info =
        @Info(
            title = "micronaut-api",
            version = "1.0.0",
            description = "Micronaut API",
            license = @License(name = "Apache 2.0")))
public class Application {
  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }
}
