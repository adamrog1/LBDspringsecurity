package com.example.springsecurity.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class UserController {

    @GetMapping("/user/getUser")
    public String getUser(){
        return "This is user";
    }

    @PutMapping("/user/updateUser")
    public String updateUser(){
        return "User updated";
    }
}
