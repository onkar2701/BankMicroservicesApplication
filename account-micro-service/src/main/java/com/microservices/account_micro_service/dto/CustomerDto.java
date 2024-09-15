package com.microservices.account_micro_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerDto {
    @NotEmpty(message = "name should not be null")
    @Size(min = 5 , max =  30, message = "The length of name should be between 5 and 30")
    private String name;

    @NotEmpty(message = "email should not be null")
    @Email(message = "email address should be valid")
    private String email;

    @NotEmpty(message = "mobile number should not be null")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number should be 10 digits number")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
