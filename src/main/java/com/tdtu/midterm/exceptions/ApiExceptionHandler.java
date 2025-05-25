package com.tdtu.midterm.exceptions;

import com.tdtu.midterm.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice("com.tdtu.midterm.api")  // Chỉ xử lý exception cho các API controllers
@ResponseBody
public class ApiExceptionHandler {

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Response handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return Response.builder()
                .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                .status("Error")
                .message("Unsupported media type: " + ex.getContentType())
                .build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status("Error")
                .message("NOT FOUND " + ex.getHttpMethod() + " " + ex.getRequestURL())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleGeneralException(Exception ex) {
        return Response.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status("Error")
                .message("An error occurred: " + ex.getMessage())
                .build();
    }
} 