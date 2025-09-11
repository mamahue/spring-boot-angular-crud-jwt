package com.example.BasicCrud.service;

import com.example.BasicCrud.model.User;
import com.example.BasicCrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {
    @Autowired
    public  UserRepository service;


    public void deleteById(Long aLong) {
        service.deleteById(aLong);
    }

    public void delete(User entity) {
        service.delete(entity);
    }


    public List<User> findAll() {
        return  service.findAll();
    }
}
