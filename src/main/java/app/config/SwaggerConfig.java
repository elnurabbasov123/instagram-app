package app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
//@OpenAPIDefinition(
//        info = @Info(
//                title = "user-controller",
//                description = "this controller is user ms controller",
//                termsOfService = "user-service",
//                contact = @Contact(
//                        name = "cabbarovali",
//                        url = "www.google.com",
//                        email = "eli.cabbarov2003@gmail.com"),
//                license = @License(name = "application.server.policy"),
//                version = "1.0.0"),
//        tags = @Tag(name = "everything", description = "life is good"),
//        servers = @Server(url = "localhost:8080",
//                          description = "user controller -> localhost:8080/users"))
public class SwaggerConfig {
}
