package kr.co.fittnerserver.common;

import com.google.gson.Gson;
import kr.co.fittnerserver.exception.JwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiControllerAdvice {

/*    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseMessage> handleExceptions(Exception e){
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(CommonErrorCode.FAIL.getCode());
        apiResponseMessage.setMessage(CommonErrorCode.FAIL.getMessage());
        apiResponseMessage.setErrorCode(CommonErrorCode.EXCEPTION.getCode());
        apiResponseMessage.setErrorMessage(CommonErrorCode.EXCEPTION.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);
    }*/

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiResponseMessage> handleDateTimeParseException(DateTimeParseException ex){
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(CommonErrorCode.FAIL.getCode());
        apiResponseMessage.setMessage(CommonErrorCode.FAIL.getMessage());
        apiResponseMessage.setErrorCode(CommonErrorCode.COMMON_FAIL.getCode());
        apiResponseMessage.setErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(apiResponseMessage);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ApiResponseMessage> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(CommonErrorCode.FAIL.getCode());
        apiResponseMessage.setMessage(CommonErrorCode.FAIL.getMessage());
        apiResponseMessage.setErrorCode(CommonErrorCode.COMMON_FAIL.getCode());
        apiResponseMessage.setErrorMessage(new Gson().toJson(errors));
        return ResponseEntity.badRequest().body(apiResponseMessage);
    }
    /**
     * RuntimeException 공통 Advice
     * @param ex
     * @return
     */
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ApiResponseMessage> handleValidationExceptions(CommonException ex){
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(CommonErrorCode.FAIL.getCode());
        apiResponseMessage.setMessage(CommonErrorCode.FAIL.getMessage());
        apiResponseMessage.setErrorCode(ex.getCode());
        apiResponseMessage.setErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);
    }



    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponseMessage> handleValidationExceptions(kr.co.fittnerserver.exception.JwtException ex){
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(CommonErrorCode.FAIL.getCode());
        apiResponseMessage.setMessage(CommonErrorCode.FAIL.getMessage());
        apiResponseMessage.setErrorCode(ex.getCode());
        apiResponseMessage.setErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponseMessage> handleParseExceptions(NullPointerException ex){
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setStatus(CommonErrorCode.FAIL.getCode());
        apiResponseMessage.setMessage(CommonErrorCode.FAIL.getMessage());
        apiResponseMessage.setErrorCode(CommonErrorCode.COMMON_FAIL.getCode());
        apiResponseMessage.setErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseMessage);
    }
}
