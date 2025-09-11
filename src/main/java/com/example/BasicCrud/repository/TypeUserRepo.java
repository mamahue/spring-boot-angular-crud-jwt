package com.example.BasicCrud.repository;

import com.example.BasicCrud.model.TypeUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypeUserRepo extends JpaRepository<TypeUser,Long> {

     Optional<TypeUser> findBytype(String type);
}
