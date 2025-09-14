package com.example.BasicCrud.repository;

import com.example.BasicCrud.model.TypeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TypeUserRepo extends JpaRepository<TypeUser,Long> {

     Optional<TypeUser> findBytype(String type);
}
