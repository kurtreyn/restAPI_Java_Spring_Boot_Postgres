package com.sbmysql.restapi.controller;

import com.sbmysql.restapi.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    UserRepo userRepo;


    @GetMapping("/")
    public String getPage() {
        return "Hello World!";
    }
}
