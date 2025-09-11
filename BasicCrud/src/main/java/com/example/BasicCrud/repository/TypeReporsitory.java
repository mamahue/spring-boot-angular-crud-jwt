package com.example.BasicCrud.repository;

import com.example.BasicCrud.model.TypeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeReporsitory  extends JpaRepository<TypeUser,Long> {
}
