package kr.co.fittnerserver.results;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.fittnerserver.util.AES256Cipher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

@Slf4j
public final class Result {

  private Result() {
  }

  public static ResponseEntity<ApiResult> created() {
    return ResponseEntity.status(201).build();
  }

  public static ResponseEntity<ApiResult> ok() {
    return ResponseEntity.ok().build();
  }

  public static ResponseEntity<ApiResult> ok(String message) {
    Assert.hasText(message, "Parameter `message` must not be blank");
    return ok(ApiResult.message(message));
  }

  public static ResponseEntity<ApiResult> ok(ApiResult payload) {
    Assert.notNull(payload, "Parameter `payload` must not be null");
    return ResponseEntity.ok(payload);
  }

  public static <T> ResponseEntity<ApiResponseMessage<T>> ok(ApiResponseMessage<T> apiResponseMessage) {
    return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
  }

  public static ResponseEntity<?> ok_enc(ApiResult payload) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String jsonString = null;
    try {
      jsonString = mapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }
    log.info(">> jsonString : {}", jsonString);
    //jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);

    String encString = AES256Cipher.encrypt(jsonString);
    log.info(">> encString : {}", encString);
    log.info(">> decString : {}", AES256Cipher.decrypt(encString));
    return ResponseEntity.ok(encString);
  }

  public static ResponseEntity<ApiResult> failure(String message) {
    return ResponseEntity.badRequest().body(ApiResult.message(message));
  }

  public static ResponseEntity<ApiResult> serverError(String message, String errorReferenceCode) {
    return ResponseEntity.status(500).body(ApiResult.error(message, errorReferenceCode));
  }

  public static ResponseEntity<ApiResult> notFound() {
    return ResponseEntity.notFound().build();
  }

  public static ResponseEntity<ApiResult> unauthenticated() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  public static ResponseEntity<ApiResult> forbidden() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
