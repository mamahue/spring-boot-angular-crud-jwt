package com.example.BasicCrud.controller;

import com.example.BasicCrud.dto.updateUser;
import com.example.BasicCrud.model.User;
import com.example.BasicCrud.repository.UserRepository;
import com.example.BasicCrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/")
public class userController {
    private PasswordEncoder passwordEncoder;


    @Autowired
    private  UserService userService;




    @GetMapping("list/")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping("{id}/")
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity<User> getUserById(@PathVariable ("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("admin/delete/{id}/")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Boolean> deleteUser(@PathVariable ("id") Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    ///
    ///     @PutMapping("users/update/{id}")
    ///    @PreAuthorize("hasAnyRole('Admin','User')")
    ///    private  ResponseEntity<updateUser> updateUser(@PathVariable("id") Long id,
    ///                                              @RequestBody  updateUser update, Authentication authentication){
    ///     User existeUser= userService.findById(id);

    ///     if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
    ///         String usernameAuth = authentication.getName();
    ///         if (!existeUser.getUsername().equals(usernameAuth)) {
    ///             return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    ///        }
    ///     }
    ///    existeUser.setName(update.name());
    ///    existeUser.setEmail(update.email());
    ///    existeUser.setUsername(update.username());
    ///    existeUser.setPassword(passwordEncoder.encode(update.password()));
    ///    if (update.password() != null && !update.password().isBlank()) {
    ///       existeUser.setPassword(passwordEncoder.encode(update.password()));
    ///    }
    ///    updateUser response = userService.save(existeUser);

    ///  return  ResponseEntity.ok(response);
    ///   }

}
