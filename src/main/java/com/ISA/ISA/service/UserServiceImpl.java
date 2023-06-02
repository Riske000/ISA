package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.RegistrationDTO;
import com.ISA.ISA.domain.DTO.UserDTO;
import com.ISA.ISA.domain.EmailDetails;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.domain.enums.UserType;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MedicalCenterRepository medicalCenterRepository;
    private static final String TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    @Override
    public User add(UserDTO userDTO){
        User user =  UserDTO.convertBack(userDTO);

        user = userRepository.save(user);
        return user;
    }
    @Transactional
    @Override
    public User registrate(RegistrationDTO registrationDTO){
        if (isEmailTaken(registrationDTO.getEmail())){
            return null;
        }
        EmailDetails emailDetails = new EmailDetails();
        String uniqueToken = generateToken();
        emailDetails.setRecipient(registrationDTO.getEmail());
        String tokenForUser = "http://localhost:8080/api/emailConfirm/confirm/" + emailDetails.getRecipient() + "/" + uniqueToken;
        emailDetails.setMsgBody(tokenForUser);
        emailService.sendConfirmationMail(emailDetails);

        User user = RegistrationDTO.convertBack(registrationDTO);
        user.setUserType(UserType.RegisteredUser);
        user.setTokenForConfirmation(uniqueToken);
        user = userRepository.save(user);
        return user;
    }

    @Override
    public boolean isEmailTaken(String email){
        Optional<User> user = userRepository.findOneByEmail(email);
        if(user.isPresent()){
            return true;
        }
        return false;
    }
    @Override
    public User edit(int userId ,int penalties){
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            return null;
        }

        user.get().setPenalties(penalties);

        return userRepository.save(user.get());
    }

    @Override
    public User updateUser(UserDTO updatedUserDTO) {
        User existingUser = userRepository.findById(updatedUserDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Ažurirajte polja korisnika na osnovu podataka iz UserDTO objekta
        existingUser.setName(updatedUserDTO.getName());
        existingUser.setSurname(updatedUserDTO.getSurname());
        existingUser.setAddress(updatedUserDTO.getAddress());
        existingUser.setCity(updatedUserDTO.getCity());
        existingUser.setCountry(updatedUserDTO.getCountry());
        existingUser.setMobilePhone(updatedUserDTO.getMobilePhone());
        existingUser.setJmbg(updatedUserDTO.getJmbg());
        existingUser.setGender(updatedUserDTO.getGender());
        existingUser.setProfession(updatedUserDTO.getProfession());
        existingUser.setJobDescription(updatedUserDTO.getJobDescription());

        return userRepository.save(existingUser);
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

    @Override
    public User getCurrentUser(String email){
        User user = findUserByEmail(email);
        return user;
    }

    public static String generateToken() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            sb.append(TOKEN_CHARS.charAt(random.nextInt(TOKEN_CHARS.length())));
        }
        return sb.toString();
    }
    @Override
    public boolean confirmMail(String email, String confirmToken){
        User user = findUserByEmail(email);

        if(user.getTokenForConfirmation().equals(confirmToken)){
            user.setConfirmed(true);
            user.setTokenForConfirmation(null);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public void updateLoyaltyCategory(User user) {
        if (user.getLoyaltyPoints() >= 0 && user.getLoyaltyPoints() <= 100) {
            user.setLoyaltyCategory("Silver");
        } else if (user.getLoyaltyPoints() >= 101 && user.getLoyaltyPoints() <= 500) {
            user.setLoyaltyCategory("Gold");
        } else if (user.getLoyaltyPoints() >= 501) {
            user.setLoyaltyCategory("Platinum");
        }
    }

}
