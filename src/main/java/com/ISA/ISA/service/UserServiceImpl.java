package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.RegistrationDTO;
import com.ISA.ISA.domain.DTO.UserDTO;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

    @Override
    public User add(RegistrationDTO registrationDTO){ //Pitaj sta vracas kod add
        User user = new User();

        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());
        user.setName(registrationDTO.getName());
        user.setSurname(registrationDTO.getSurname());
        user.setAddress(registrationDTO.getAddress());
        user.setCity(registrationDTO.getCity());
        user.setCountry(registrationDTO.getCountry());
        user.setJmbg(registrationDTO.getJmbg());
        user.setGender(registrationDTO.getGender());
        user.setProfession(registrationDTO.getProfession());
        user.setJobDescription(registrationDTO.getJobDescription());
        user.setUserType(registrationDTO.getUserType());
        user.setMedicalCenter(medicalCenterRepository.getReferenceById(registrationDTO.getMedicalCenterID()));

        userRepository.save(user);
        return user;
    }

    @Override
    public User edit(UserDTO userDTO){
        User user = new User();
        user = UserDTO.convertBack(userDTO); // pitaj da li moze ovako zbog sifre
        // pitaj da kako da update u repo i dodas
        return user;
    }

    @Override
    public void delete(int id){
        userRepository.deleteById(id);    // pitaj
    }

    @Override
    public User findById(int id){
        return userRepository.getReferenceById(id); // pitaj jel sme getReferenceById
    }

    @Override
    public List<User> getAll(){
        return userRepository.findAll();
    }
}
