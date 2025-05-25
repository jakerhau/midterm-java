package com.tdtu.midterm.constant;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // General
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase()),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase()),

    // Product
    PRODUCT_NAME_EXIST(400, HttpStatus.BAD_REQUEST, "Product name already exists"),
    PRODUCT_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Product not found"),
    PRODUCT_FIELD_INVALID(400, HttpStatus.BAD_REQUEST, "Product field invalid");

    private int code;
    private HttpStatus httpStatus;
    private String message;

    ErrorCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
