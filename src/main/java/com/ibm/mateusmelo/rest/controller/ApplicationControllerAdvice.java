package com.ibm.mateusmelo.rest.controller;

import com.ibm.mateusmelo.exception.NotFoundException;
import com.ibm.mateusmelo.exception.OrderNotFoundException;
import com.ibm.mateusmelo.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleNotFoundException(NotFoundException exception) {
        return new ApiErrors(exception.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleOrderNotFoundException(OrderNotFoundException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiErrors handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .toList();

        return new ApiErrors(errors);
    }
}
