package com.example.courseworkisbd.service.impl;


import com.example.courseworkisbd.dto.SportDirectorDto;
import com.example.courseworkisbd.entity.FootballClub;
import com.example.courseworkisbd.entity.Role;
import com.example.courseworkisbd.entity.SportDirector;
import com.example.courseworkisbd.repository.RoleRepository;
import com.example.courseworkisbd.repository.UserRepository;
import com.example.courseworkisbd.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SportDirector getSportDirectorByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(SportDirectorDto sportDirectorDto) {
        SportDirector sportDirector = new SportDirector();
        sportDirector.setName(sportDirectorDto.getFirstName() + " " + sportDirectorDto.getLastName());
        sportDirector.setEmail(sportDirectorDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        sportDirector.setPassword(passwordEncoder.encode(sportDirectorDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        sportDirector.setRoles(Arrays.asList(role));
        userRepository.save(sportDirector);
    }

    @Override
    public SportDirector findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<SportDirectorDto> findAllUsers() {
        List<SportDirector> sportDirectors = userRepository.findAll();
        return sportDirectors.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    public void updateUser(SportDirector sportDirector, FootballClub footballClub) {
        userRepository.findByEmail(sportDirector.getEmail()).setFootballClub(footballClub);
    }

    private SportDirectorDto convertEntityToDto(SportDirector sportDirector){
        SportDirectorDto sportDirectorDto = new SportDirectorDto();
        String[] name = sportDirector.getName().split(" ");
        sportDirectorDto.setFirstName(name[0]);
        sportDirectorDto.setLastName(name[1]);
        sportDirectorDto.setEmail(sportDirector.getEmail());
        return sportDirectorDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
