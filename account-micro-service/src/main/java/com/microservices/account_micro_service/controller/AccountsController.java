package com.microservices.account_micro_service.controller;

import com.microservices.account_micro_service.constants.AccountsConstants;
import com.microservices.account_micro_service.dto.CustomerDto;
import com.microservices.account_micro_service.dto.ResponseDto;
import com.microservices.account_micro_service.service.AccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    private AccountsService accountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){

        accountsService.createAccount(customerDto); //If this method doesn't throw any exception
        //then only the next line will be executed

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_201));

    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                               @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number should be 10 digits")
                                                               String mobileNumber){
        return new ResponseEntity<>(accountsService.fetchAccount(mobileNumber), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number should be 10 digits")
                                                         String mobileNumber){
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if(!isDeleted)
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(),"Error Occured while deleting account"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.toString(),AccountsConstants.MESSAGE_200));
    }
}
