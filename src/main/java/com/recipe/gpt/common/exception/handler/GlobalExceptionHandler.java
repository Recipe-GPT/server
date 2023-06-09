package com.recipe.gpt.common.exception.handler;

import com.recipe.gpt.app.web.response.ErrorResponse;
import com.recipe.gpt.app.web.response.ValidationErrorResponse;
import com.recipe.gpt.common.exception.GeneralHttpException;
import com.recipe.gpt.common.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralHttpException.class)
    public ResponseEntity<ErrorResponse> generalHttpExceptionHandler(GeneralHttpException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .statusCode(e.getHttpStatus().value())
            .reason(e.getHttpStatus().getReasonPhrase())
            .message(e.getMessage())
            .build();
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableExceptionHandler(
        HttpMessageNotReadableException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .reason(e.getMessage())
            .message("Json 타입 메시지를 읽을 수 없습니다.")
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> methodArgumentNotValidExceptionHandler(
        MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : e.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(errorMap);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ValidationErrorResponse> constraintViolationExceptionHandler(
        ConstraintViolationException e) {
        Map<String, String> errorMap = new HashMap<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            errorMap.put(
                    propertyPath.substring(propertyPath.lastIndexOf(".") + 1),
                    violation.getMessage());
        }
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(errorMap);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeExceptionHandler(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .reason(e.getMessage())
            .message("알 수 없는 에러입니다. ")
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WebClientException.class)
    public ResponseEntity<ErrorResponse> webClientExceptionHandler(WebClientResponseException e) {

        String responseBody = e.getResponseBodyAsString();
        // [1] responseBody 에서 errorMessage 추출
        String errorMessage = extractErrorMessageFromResponseBody(responseBody);

        // [2] errorResponse builder
        ErrorResponse errorResponse = ErrorResponse.builder()
            .statusCode(e.getStatusCode().value())
            .reason(e.getMessage())
            .message(errorMessage)
            .build();
        return new ResponseEntity<>(errorResponse, e.getStatusCode());
    }

    private String extractErrorMessageFromResponseBody(String responseBody) {
        Map<String, Object> responseMap = JsonUtils.readValue(responseBody, Map.class);
        return responseMap.get("message").toString();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(
        MaxUploadSizeExceededException e) {

        ErrorResponse response = ErrorResponse.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .reason(e.getMessage())
            .message("파일 크기가 32MB 용량을 초과했습니다.")
            .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
