package kr.co.fittnerserver.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Slf4j
public class FirebaseConfig {


    @Value("${spring.config.activate.on-profile}")
    private String profile;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if(profile.equals("prod")){
            FileInputStream aboutFirebaseFile = new FileInputStream(String.valueOf(ResourceUtils.getFile("/app/fittner/fcm")));
            FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(aboutFirebaseFile))
                    .build();
            return FirebaseApp.initializeApp(options);
        }else{
            FileInputStream aboutFirebaseFile = new FileInputStream(String.valueOf(ResourceUtils.getFile("classpath:fittner-88bd2-firebase-adminsdk-fbsvc-e75efcd2f4.json")));
            FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(aboutFirebaseFile))
                    .build();
            return FirebaseApp.initializeApp(options);
        }
    }
    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}


