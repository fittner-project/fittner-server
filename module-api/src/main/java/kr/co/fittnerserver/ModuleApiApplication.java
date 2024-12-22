package kr.co.fittnerserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ModuleApiApplication {

    public static void main(String[] args) throws Exception {
        //배포 테스트9
        SpringApplication.run(ModuleApiApplication.class, args);
    }

}
