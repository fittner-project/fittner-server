package kr.co.fittnerserver.util;

import com.google.firebase.messaging.*;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmTokenUtil {

    //private final FirebaseMessaging firebaseMessaging;


/*    public void sendPush(String title, String body, List<String> targetToken) {
        List<MulticastMessage> multicastMessages = makeMultiMessages(title, body, targetToken);
        multicastMessages.forEach(message -> {
            try {
                firebaseMessaging.sendEachForMulticast(message);
            } catch (FirebaseMessagingException e) {
                log.error("sendPush error", e);
                throw new CommonException(CommonErrorCode.PUSH_ERROR.getCode(), CommonErrorCode.PUSH_ERROR.getMessage());
            }
        });

    }*/

    public static List<MulticastMessage> makeMultiMessages(String title, String body, List<String> targetToken) {
        List<MulticastMessage> multicastMessages = new ArrayList<>();
        int batchSize = 100;
        for (int i = 0; i < targetToken.size(); i += batchSize) {
            List<String> batchTokens = targetToken.subList(i, Math.min(i + batchSize, targetToken.size()));
            MulticastMessage message = makeMultiMessage(title, body, batchTokens);
            multicastMessages.add(message);
        }

        return multicastMessages;
    }

    public static MulticastMessage makeMultiMessage(String title, String body, List<String> targetToken) {
        Aps aps = Aps.builder()
                .putCustomData("sound", "default")
                .putCustomData("vibrate", "default")
                .build();

        ApnsConfig apnsConfig = ApnsConfig.builder()
                .setAps(aps)
                .build();

        Notification notification = Notification
                .builder()
                .setTitle(title)
                .setBody(body)
                .build();

        return MulticastMessage.builder()
                .setNotification(notification)
                .setAndroidConfig(AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build())
                .setApnsConfig(apnsConfig)
                //.putData("msgGroupId", msgGroupId)
                .addAllTokens(targetToken)
                .build();
    }

}
