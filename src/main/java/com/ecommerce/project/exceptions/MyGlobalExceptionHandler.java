package com.ecommerce.project.exceptions;

import com.ecommerce.project.payload.APIResonse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            response.put(fieldName, errorMessage);
        });
        return new ResponseEntity<Map<String, String>> (response,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<APIResonse> myResourceNotFoundException(ResourceNotFoundException e){
        String message = e.getMessage();
        APIResonse apiResonse = new APIResonse(message, false);
        return new ResponseEntity<>(apiResonse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public  ResponseEntity<APIResonse> myAPIException(APIException e){
        String message = e.getMessage();
        APIResonse apiResonse = new APIResonse(message, false);
        return new ResponseEntity<>(apiResonse, HttpStatus.BAD_REQUEST);
    }
}
