package com.example.BasicCrud.dto;

import com.example.BasicCrud.model.TypeUser;

public record registerDto(
        String name,
        String email,
        String password,
        String username,
        String type

) {

}
