package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.RegistrationDTO;
import com.ISA.ISA.domain.DTO.UserDTO;
import com.ISA.ISA.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User add(UserDTO userDTO);
    User registrate(RegistrationDTO registrationDTO);
    User edit(int userId, int penalties);

    User updateUser(UserDTO updatedUserDTO);

    void delete(int id);
    Optional<User> findById(int id);
    List<User> getAll();
    User findUserByEmail(String email);
    boolean isEmailTaken(String email);
    User getCurrentUser(String email);
    boolean confirmMail(String email, String confirmToken);

    void updateLoyaltyCategory(User user);

    List<User> getAllAdministratorsForMedicalCenter(int medicalCenterId);

    User changePassword(String email, String newPassword);
}
