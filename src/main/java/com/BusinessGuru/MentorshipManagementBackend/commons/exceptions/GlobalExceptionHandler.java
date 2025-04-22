package com.BusinessGuru.MentorshipManagementBackend.commons.exceptions;


import com.BusinessGuru.MentorshipManagementBackend.commons.ApiResponse;
import com.BusinessGuru.MentorshipManagementBackend.commons.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse<String> apiResponse = new ApiResponse<>(new Meta("Resource not found",false),message);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<String>> ApiExceptionHandler(ApiException ex){
        String message = ex.getMessage();
        ApiResponse<String> apiResponse = new ApiResponse<>(new Meta(message,false),message);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }
}
