package com.example.springsecurity.Controllers;

import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
public class AdminController {

    @GetMapping("/admin/getAdmin")
    public String getAdmin(){
        return "This is admin";
    }

    @PostMapping("/admin/createUser")
    public String createUser(){
        return "User Created";
    }

    @DeleteMapping("/admin/deleteUser")
    public String deleteUser(){
        return "User Deleted";
    }
}
