package kr.co.fittnerserver;


import kr.co.fittnerserver.util.AES256Cipher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ModuleApiApplication {

    public static void main(String[] args) throws Exception {
        //배포 테스트4
        SpringApplication.run(ModuleApiApplication.class, args);
    }

}
