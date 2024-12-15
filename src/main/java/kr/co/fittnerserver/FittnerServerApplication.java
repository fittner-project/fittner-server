package kr.co.fittnerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FittnerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FittnerServerApplication.class, args);
    }

}
