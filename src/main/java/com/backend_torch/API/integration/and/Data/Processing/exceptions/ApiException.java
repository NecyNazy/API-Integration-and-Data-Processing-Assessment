package com.backend_torch.API.integration.and.Data.Processing.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException{
    private final String status;
    private final int httpStatus;
    public ApiException(String status, String message) {
        super(message);
        this.status = status;
        this.httpStatus = 400;
    }
    public ApiException(String status, String message, int httpStatus) {
        super(message);
        this.status = status;
        this.httpStatus = httpStatus;
    }
}
