package com.example.BasicCrud.service;

import com.example.BasicCrud.dto.updateUser;
import com.example.BasicCrud.model.User;
import com.example.BasicCrud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final   UserRepository userRepository;


    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }

    public void delete(User entity) {
        userRepository.delete(entity);
    }


    public User save (User entity) {
        return userRepository.save(entity);
    }



    public List<User> findAll() {
        return  userRepository.findAll();
    }

    public  User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }
}
