package kr.co.fittnerserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ModuleBatchApplication {
    //배포를 위한 커밋
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ModuleBatchApplication.class, args);
    }

}
