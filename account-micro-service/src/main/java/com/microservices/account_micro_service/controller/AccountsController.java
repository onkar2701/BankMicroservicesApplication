package com.microservices.account_micro_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }
}
