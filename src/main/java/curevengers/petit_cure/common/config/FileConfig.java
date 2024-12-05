package curevengers.petit_cure.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
@ComponentScan(basePackages = "curevengers.petit_cure.common.util")
public class FileConfig {

    @Bean(name = "uploadPath")
    public String uploadPath() {
        return "D:\\Workspace\\fileUpload";
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
