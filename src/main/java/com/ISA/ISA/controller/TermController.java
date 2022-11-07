package com.ISA.ISA.controller;

import com.ISA.ISA.domain.DTO.TermDTO;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/loads")
public class TermController {
    @Autowired
    private TermService termService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody TermDTO termDTO){
        Term term = termService.add(termDTO);

        if(term == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(term, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody TermDTO termDTO){
        Term term = termService.edit(termDTO);

        if(term == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(term, HttpStatus.OK);
    }

    @DeleteMapping(path = "{/id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        termService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "{/id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Term term = termService.findById(id);

        if(term == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(term, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Term> terms = termService.getAll();

        if(terms == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(terms, HttpStatus.OK);
    }
}
