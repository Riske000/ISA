package com.ISA.ISA.controller;

import com.ISA.ISA.domain.DTO.QuestionnaireDTO;
import com.ISA.ISA.domain.DTO.UserDTO;
import com.ISA.ISA.domain.Questionnaire;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/questionanaire")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody QuestionnaireDTO questionnaireDTO){
        Questionnaire questionnaire = questionnaireService.add(questionnaireDTO);

        if(questionnaire == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(questionnaire, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody QuestionnaireDTO questionnaireDTO){
        Questionnaire questionnaire = questionnaireService.edit(questionnaireDTO);

        if(questionnaire == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(questionnaire, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        questionnaireService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Questionnaire> questionnaire = questionnaireService.findById(id);

        if(questionnaire.isEmpty()){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(questionnaire.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Questionnaire> questionnaires = questionnaireService.getAll();

        if(questionnaires == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(questionnaires, HttpStatus.OK);
    }



}
