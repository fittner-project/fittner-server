package kr.co.fittnerserver;


import kr.co.fittnerserver.util.AES256Cipher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModuleApiApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ModuleApiApplication.class, args);
    }

}
