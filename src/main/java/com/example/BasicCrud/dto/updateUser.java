package com.example.BasicCrud.dto;

public record updateUser(String name,
                         String email,
                         String newpassword,
                         String username ,
                         String type) {
}
