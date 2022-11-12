package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.RegistrationDTO;
import com.ISA.ISA.domain.DTO.UserDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

    @Override
    public User add(UserDTO userDTO){
        User user =  UserDTO.convertBack(userDTO);

        user = userRepository.save(user);
        return user;
    }
    @Override
    public User registrate(RegistrationDTO registrationDTO){
        User user = RegistrationDTO.convertBack(registrationDTO);

        user = userRepository.save(user);
        return user;
    }
    @Override
    public User edit(UserDTO userDTO){
        Optional<User> user = userRepository.findById(userDTO.getId());
        if (user.isEmpty()){
            return null;
        }

        user.get().setEmail(userDTO.getEmail());
        user.get().setName(userDTO.getName());
        user.get().setSurname(userDTO.getSurname());
        user.get().setAddress(userDTO.getAddress());
        user.get().setCity(userDTO.getCity());
        user.get().setCountry(userDTO.getCountry());
        user.get().setMobilePhone(userDTO.getMobilePhone());
        user.get().setJmbg(userDTO.getJmbg());
        user.get().setGender(userDTO.getGender());
        user.get().setProfession(userDTO.getProfession());
        user.get().setJobDescription(userDTO.getJobDescription());
        user.get().setUserType(userDTO.getUserType());
        Optional<MedicalCenter> medicalCenter = medicalCenterRepository.findById(userDTO.getMedicalCenterID());
        if(medicalCenter.isPresent()){
            user.get().setMedicalCenter(medicalCenter.get());
        }

        return userRepository.save(user.get());
    }

    @Override
    public void delete(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setDeleted(true);
        }
    }

    @Override
    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email){
        Optional<User> user = userRepository.findOneByEmail(email);

        if (user.isEmpty()){
            return null;
        }

        return user.get();
    }
}
