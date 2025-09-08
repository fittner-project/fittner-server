package kr.co.fittnerserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ModuleBatchApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ModuleBatchApplication.class, args);
    }

}
