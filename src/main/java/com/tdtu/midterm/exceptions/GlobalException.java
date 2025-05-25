package com.tdtu.midterm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice("com.tdtu.midterm.controller")  // Chỉ xử lý exception cho các view controllers
public class GlobalException {

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, Model model) {
        model.addAttribute("status", HttpStatus.METHOD_NOT_ALLOWED);
        model.addAttribute("message", "Unsupported media type: " + ex.getContentType());
        return "error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND);
        model.addAttribute("message", "NOT FOUND " + ex.getHttpMethod() + " " + ex.getRequestURL());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        model.addAttribute("message", "An error occurred: " + ex.getMessage());
        return "error";
    }
}
