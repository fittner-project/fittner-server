package kr.co.fittnerserver.util;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;

@Slf4j
@Component
public class AES256Cipher {

    private static String secretKey; // static 필드로 변경

    @Value("${aes.secret-key}")
    private String injectedSecretKey;


    @PostConstruct
    public void init() {
        secretKey = injectedSecretKey;  // 초기화 후 secretKey 설정
    }

    public static final String ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    public static final String ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

    private static final byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00 };

    /**
     * 비밀키를 생성한다.
     */
    public static String generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance("AES", "SunJCE");
        } catch (NoSuchProviderException nspe) {
            keygen = KeyGenerator.getInstance("AES");
        }
        keygen.init(256);

        SecretKey skey = keygen.generateKey();
        return toHex(skey.getEncoded());
    }

    /**
     * Hex 값으로 변환한다.
     */
    private static String toHex(byte ba[]) {
        StringBuffer buf = new StringBuffer(ba.length * 2);
        for (int i = 0; i < ba.length; i++) {
            int intVal = ba[i] & 0xff;
            if (intVal < 0x10)
                buf.append("0");
            buf.append(Integer.toHexString(intVal));
        }
        return buf.toString();
    }

    public static String encrypt(String str) throws Exception{
        return encryptECB(str);
    }

    public static String decrypt(String str) throws Exception {
        return decryptECB(str);
    }

    /**
     * 사용자 정의 설정으로 암호화를 수행한다.
     * @param str
     * @param algorithm
     * @param secretKey
     * @return
     */
    public static String encrypt(String str, String algorithm, String secretKey) {

        if(ObjectUtils.isEmpty(str) || ObjectUtils.isEmpty(algorithm) || ObjectUtils.isEmpty(secretKey)){
            return "";
        }
        String result = "";

        try {
            byte[] textBytes = str.getBytes(StandardCharsets.UTF_8);
            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKeySpec newKey = new SecretKeySpec(secretKey.substring(0, 32).getBytes(StandardCharsets.UTF_8), "AES");

            if(algorithm.equals(ALGORITHM_CBC)){
                AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
            }else if (algorithm.equals(ALGORITHM_ECB)){
                cipher.init(Cipher.ENCRYPT_MODE, newKey);
            }
            result = Base64.encodeBase64String(cipher.doFinal(textBytes));
        }catch(Exception e){
            log.error("AES256Cipher encrypt : {}", e.getMessage());
        }
        return result;
    }

    /**
     * 암호화를 수행한다.
     */
    public static String encryptCBC(String str) throws Exception {
        byte[] textBytes = str.getBytes(StandardCharsets.UTF_8);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        SecretKeySpec newKey = new SecretKeySpec(secretKey.substring(0, 32).getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        return Base64.encodeBase64String(cipher.doFinal(textBytes));
    }

    public static String encryptECB(String str) throws Exception {
        if (str == null) return null;
        String strKey = getSHA512();
        final Cipher encryptCipher = Cipher.getInstance("AES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, generateMySQLAESKey(strKey));
        return new String(Hex.encodeHex(encryptCipher.doFinal(str.getBytes(StandardCharsets.UTF_8)))).toUpperCase();
    }

    /**
     * 사용자 정의된 설정으로 복호화를 수행한다.
     * @param str
     * @param algorithm
     * @param secretKey
     * @return
     */
    public static String decrypt(String str, String algorithm, String secretKey) {

        if(ObjectUtils.isEmpty(str) || ObjectUtils.isEmpty(algorithm) || ObjectUtils.isEmpty(secretKey)){
            return "";
        }
        String result = "";

        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            byte[] textBytes = Base64.decodeBase64(str);
            SecretKeySpec newKey = new SecretKeySpec(secretKey.substring(0, 32).getBytes(StandardCharsets.UTF_8), "AES");

            if(algorithm.equals(ALGORITHM_CBC)){
                AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            }else if(algorithm.equals(ALGORITHM_ECB)){
                cipher.init(Cipher.DECRYPT_MODE, newKey);
            }
            result = new String(cipher.doFinal(textBytes), StandardCharsets.UTF_8);
        }catch(Exception e){
            log.error("AES256Cipher decrypt : {}", e.getMessage());
        }
        return result;
    }

    /**
     * 복호화를 수행한다.
     */
    public static String decryptCBC(String str) throws Exception {
        byte[] textBytes = Base64.decodeBase64(str);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(secretKey.substring(0, 32).getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        return new String(cipher.doFinal(textBytes), StandardCharsets.UTF_8);
    }

    public static String decryptECB(String str) throws Exception {
        if (str == null || "".equals(str)) return null;
        String strKey = getSHA512();
        final Cipher decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey(strKey));
        return new String(decryptCipher.doFinal(Hex.decodeHex(str.toCharArray())));
    }

    /**
     * 알고리즘에 따라 HMAC 값을 생성한다.
     *
     * @param input
     *            HMAC을 생성하는데 사용할 message
     * @return hexa string으로 표현한 HMAC 결과
     */
    public static String generateHMac(String input) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), "HMACSHA256");

        Mac mac = Mac.getInstance("HMACSHA256");
        mac.init(skeySpec);

        byte[] result = mac.doFinal(input.getBytes());
        return toHex(result);
    }

    /**
     * SHA-512 형태 Secret Key 반환
     * @return
     * @throws Exception
     */
    public static String getSHA512() throws Exception{
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.reset();
        digest.update(secretKey.getBytes(StandardCharsets.UTF_8));
        return String.format("%0128x", new BigInteger(1, digest.digest()));
    }

    private static Key generateMySQLAESKey(final String strKey) throws Exception {
        final byte[] finalKey = new byte[16];
        int i = 0;
        for(byte b : strKey.getBytes(StandardCharsets.UTF_8)){
            finalKey[i++%16] ^= b;
        }
        return new SecretKeySpec(finalKey, "AES");
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String key = AES256Cipher.generateSecretKey();
        log.info(">> Generated API Key !! : {}", key);
    }
}
