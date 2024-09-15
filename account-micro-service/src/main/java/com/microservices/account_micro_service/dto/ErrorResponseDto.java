package com.microservices.account_micro_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDto { //This can be useful when we get any error
    private String apiPath; //Api path for which we got error
    private HttpStatus httpStatus;  //status code like 400, 500
    private String errorMessage;
    private LocalDateTime errorTimestamp;
}
