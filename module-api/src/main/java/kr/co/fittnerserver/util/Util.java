package kr.co.fittnerserver.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

@Slf4j
@Component
public class Util {

    /**
     * 일자 기반 랜덤문자열 생성
     * 현재시간의 yyyyMMddHHmmSSSS 형식에서 ddHHmmSSSS만 추출 후 알파벳으로 변환
     * 알파벳 변환시 대,소문자 및 변환할 자리 index는 랜덤
     *
     * @param length
     * @return 랜덤문자열
     */
    public static String convertTimeToRandomAlpha(int length) {
        // 현재 시간을 'yyyyMMddHHmmSSSS' 형식으로 포맷팅
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String currentTime = sdf.format(new Date());

        // 'ddHHmmSSSS'만 추출
        String ddHHmmSSSS = currentTime.substring(6, 17); // 'ddHHmmSSSS' 추출 (6번째에서 17번째까지)

        // 숫자를 알파벳으로 변환한 결과를 저장할 StringBuilder
        StringBuilder result = new StringBuilder();

        // 랜덤 객체 생성
        Random random = new Random();

        // 'ddHHmmSSSS' 문자열을 하나씩 처리
        for (char digit : ddHHmmSSSS.toCharArray()) {
            int digitValue = Character.getNumericValue(digit); // 숫자로 변환

            // 랜덤하게 알파벳으로 변환 (숫자 -> 알파벳, 대소문자 랜덤)
            if (random.nextBoolean()) { // 랜덤으로 숫자 또는 알파벳으로 처리
                // 숫자 그대로 추가
                result.append(digit);
            } else {
                // 알파벳으로 변환 (0 -> a, 1 -> b, ..., 9 -> j)
                char letter = (char) ('a' + digitValue);

                // 대소문자 랜덤
                if (random.nextBoolean()) {
                    result.append(Character.toUpperCase(letter)); // 대문자
                } else {
                    result.append(letter); // 소문자
                }
            }
        }

        //최종 return
        String finalResult = result.toString();
        log.info(finalResult);
        //추가문자열(자릿수 패딩)
        if(length < 10){
            finalResult = finalResult.substring(0,length);
        }else{
            finalResult = RandomStringUtils.randomAlphanumeric(length-10)+finalResult;
        }

        return finalResult;
    }

    /**
     * 파일 암호화 메서드
     *
     * @param fileData, fileAesKey
     * @return 암호화 byte
     */
    public static byte[] encryptFile(byte[] fileData, String fileAesKey) throws Exception {
        SecretKey secretKey = new SecretKeySpec(fileAesKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(fileData);
    }

    /**
     * 파일 복호화 메서드
     *
     * @param encryptedData, fileAesKey
     * @return 복호화 byte
     */
    public static byte[] decryptFile(byte[] encryptedData, String fileAesKey) throws Exception {
        SecretKey secretKey = new SecretKeySpec(fileAesKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedData);
    }

    /**
     * date 포멧
     *
     * @param format
     * @return date 포맷 일자
     */
    public static String getFormattedToday(String format){
        if(!StringUtils.isEmpty(format)){
            format = "yyyyMMdd";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 파일명 저장시 공백 및 특수문자 제거
     *
     * @param originalFileName
     * @return 특수문자 제거 문자열
     */
    public static String sanitizeFileName(String originalFileName) {
        // 확장자 분리
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        // 특수 문자와 공백을 언더스코어(_)로 치환
        baseName = baseName.replaceAll("[^a-zA-Z0-9가-힣]", "_");

        return baseName + "." + extension;
    }
}
