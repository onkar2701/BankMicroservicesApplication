package com.microservices.account_micro_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {
    @NotEmpty(message = "account number should not be null")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "account number should be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "account type should not be null")
    private String accountType;

    @NotEmpty(message = "branch address should not be null")
    private String branchAddress;
}
