package com.ISA.ISA.controller;


import com.ISA.ISA.domain.DTO.LoginDTO;
import com.ISA.ISA.domain.DTO.LoginResponseDTO;
import com.ISA.ISA.domain.DTO.RegistrationDTO;
import com.ISA.ISA.domain.DTO.UserDTO;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.security.TokenUtils;
import com.ISA.ISA.service.UserService;
import com.ISA.ISA.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "Authorization")
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenUtils tokenUtils;
    @PostMapping
    public ResponseEntity<?> add(@RequestBody UserDTO userDTO){
        User user = userService.add(userDTO);

        if(user == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfCurrentMonth = LocalDate.now().withDayOfMonth(1);
        User user = userService.findUserByEmail(loginDTO.getEmail());
        if (user != null && firstDayOfCurrentMonth.equals(today)) {
            userService.edit(user.getId(), 0);
        }
        String encripted = passwordEncoder.encode(user.getPassword());
        if(user == null || !passwordEncoder.matches(loginDTO.getPassword(),encripted)) {
            return ResponseEntity.ok( HttpStatus.UNAUTHORIZED);
        }
        if (!user.isConfirmed()){
            return  ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        String token = tokenUtils.generateToken(user.getEmail(), user.getRole());
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(token);
        return ResponseEntity.ok(responseDTO);
    }
    @PostMapping(path = "/registration")
    public ResponseEntity<?> registrate(@RequestBody RegistrationDTO registrationDTO){
        User user = userService.registrate(registrationDTO);

        if(user == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> edit(@RequestBody UserDTO userDTO){
        return null;
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<User> user = userService.findById(id);

        if(user.isEmpty()){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<User> users = userService.getAll();

        if(users == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/getCurrentUser/{email}")
    public ResponseEntity<?> getCurrentUser(@PathVariable String email){
        User user = userService.getCurrentUser(email);

        if(user == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
