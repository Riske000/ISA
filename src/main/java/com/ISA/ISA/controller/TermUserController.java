package com.ISA.ISA.controller;

import com.ISA.ISA.domain.DTO.TermUserDTO;
import com.ISA.ISA.domain.TermUser;
import com.ISA.ISA.service.TermUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/loads")
public class TermUserController {

    @Autowired
    private TermUserService termUserService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody TermUserDTO termUserDTO){
        TermUser termUser = termUserService.add(termUserDTO);

        if(termUser == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(termUser, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody TermUserDTO termUserDTO){
        TermUser termUser = termUserService.edit(termUserDTO);

        if(termUser == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(termUser, HttpStatus.OK);
    }

    @DeleteMapping(path = "{/id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        termUserService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "{/id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        TermUser termUser = termUserService.findById(id);

        if(termUser == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(termUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<TermUser> termUsers = termUserService.getAll();

        if(termUsers == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(termUsers, HttpStatus.OK);
    }
}
