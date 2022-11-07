package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.RegistrationDTO;
import com.ISA.ISA.domain.DTO.UserDTO;
import com.ISA.ISA.domain.User;

import java.util.List;

public interface UserService {

    User add(RegistrationDTO registrationDTO);
    User edit(UserDTO userDTO);
    void delete(int id);
    User findById(int id);
    List<User> getAll();
}
