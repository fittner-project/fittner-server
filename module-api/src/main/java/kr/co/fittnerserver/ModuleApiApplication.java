package kr.co.fittnerserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModuleApiApplication {

    public static void main(String[] args) throws Exception {
        //API 배포
        SpringApplication.run(ModuleApiApplication.class, args);
    }

}
