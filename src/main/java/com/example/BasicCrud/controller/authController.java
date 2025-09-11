package com.example.BasicCrud.controller;

import com.example.BasicCrud.dto.logindto;
import com.example.BasicCrud.dto.registerDto;
import com.example.BasicCrud.model.User;
import com.example.BasicCrud.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/auth/")
public class authController {

    @Autowired
    private AuthService authService;

    @PostMapping("register/")
    private ResponseEntity<User> registerUser (@RequestBody registerDto dto) throws URISyntaxException {
        try {
            User crateUser = authService.register(dto);
            return  ResponseEntity.created(new URI("auth/register"+crateUser.getId())).body(crateUser);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("login/")
    private  ResponseEntity<User> loginUser(@RequestBody logindto dto) throws URISyntaxException {

        try {

            User loginUser = authService.login(dto);
            return ResponseEntity.ok(loginUser);
        }catch ( Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


}
