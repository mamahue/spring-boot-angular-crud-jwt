package com.example.BasicCrud.service;

import com.example.BasicCrud.dto.autResponse;
import com.example.BasicCrud.dto.logindto;
import com.example.BasicCrud.dto.registerDto;
import com.example.BasicCrud.model.TypeUser;
import com.example.BasicCrud.model.User;
import com.example.BasicCrud.repository.TypeUserRepo;
import com.example.BasicCrud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
 private final UserRepository userRepository;
 private  final TypeUserRepo typeUserRepo;
 private final PasswordEncoder passwordEncoder;
 private final jwUtil jwUtil;
 @Autowired
 private UserService userService;
    public autResponse register (registerDto dto){
        TypeUser type =  typeUserRepo.findBytype(dto.type())
                .orElseThrow(()-> new RuntimeException("Rol no encontrado"));
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setTypeUser(type);
        User saveduser = userService.save(user);
        String token = jwUtil.generateToken(dto.username(),user.getTypeUser().getType());
   return new autResponse(token,saveduser.getUsername(),saveduser.getTypeUser().getType());
    }

    public autResponse login(logindto dto){
        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(dto.password(), user.getPassword())){
            throw  new RuntimeException("credenciales no validas");
        }

        String token = jwUtil.generateToken(user.getUsername(), user.getTypeUser().getType());

        return  new autResponse(token, user.getUsername(),user.getTypeUser().getType());

    }
}
