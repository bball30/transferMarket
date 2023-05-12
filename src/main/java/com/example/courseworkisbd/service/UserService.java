package com.example.courseworkisbd.service;


import com.example.courseworkisbd.dto.SportDirectorDto;
import com.example.courseworkisbd.entity.SportDirector;

import java.util.List;

public interface UserService {
    void saveUser(SportDirectorDto sportDirectorDto);

    SportDirector findByEmail(String email);

    List<SportDirectorDto> findAllUsers();
}
