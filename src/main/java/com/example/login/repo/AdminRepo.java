package com.example.login.repo;

import com.example.login.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin,Long> {

    Optional<Admin> findByUsername(String name);
}
