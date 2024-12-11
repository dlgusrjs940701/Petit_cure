package curevengers.petit_cure.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.io.File;

@Configuration
@ComponentScan(basePackages = "curevengers.petit_cure.common.util")
public class FileConfig {

    @Bean(name = "uploadPath")
    public String uploadPath() {
        // 실행 중인 애플리케이션 디렉토리 + /static/images 경로
        String path = System.getProperty("user.dir") + "/src/main/resources/static/images";

        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리가 없으면 생성
        }
        return path;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
