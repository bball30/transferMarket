package com.example.courseworkisbd.repository;


import com.example.courseworkisbd.entity.SportDirector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SportDirector, Long> {
    SportDirector findByEmail(String email);

}
