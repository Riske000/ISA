package com.ISA.ISA.controller;

import com.ISA.ISA.domain.DTO.ReserveTermDTO;
import com.ISA.ISA.domain.DTO.TermDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.service.MedicalCenterService;
import com.ISA.ISA.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.*;


@RestController
@RequestMapping("api/term")
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

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        termService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Term> term = termService.findById(id);

        if(term.isEmpty()){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(term.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Term> terms = termService.getAll();

        if(terms == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(terms, HttpStatus.OK);
    }

    @GetMapping("/getTermsForCenter/{medId}")
    public ResponseEntity<?> getAllForSelectedCenter(@PathVariable int medId){
        List<Term> terms = termService.getAllForSelectedCenter(medId);
        if(terms == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(terms, HttpStatus.OK);
    }

    @PostMapping("/reserveTerm/")
    public ResponseEntity<?> reserveTerm(@RequestBody ReserveTermDTO reserveTermDTO){
        Term term = termService.reserveTerm(Integer.parseInt(reserveTermDTO.getTermId()),
                Integer.parseInt( reserveTermDTO.getUserId()));
        if(term == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(term, HttpStatus.OK);
    }

    @PostMapping("/cancelTerm/")
    public ResponseEntity<?> cancelTerm(@RequestBody ReserveTermDTO reserveTermDTO){
        Term term = termService.cancelTerm(Integer.parseInt(reserveTermDTO.getTermId()),
                Integer.parseInt( reserveTermDTO.getUserId()));
        if(term == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(term, HttpStatus.OK);
    }

    @GetMapping("/getReservedTermForUser/{userId}")
    public ResponseEntity<?> getReservedTermForUser(@PathVariable int userId){
        List<Term> terms = termService.getReservedTermForUser(userId);

        if(terms == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(terms, HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<?> getAllFreeTerms() {

        return new ResponseEntity<>(termService.getAllFreeTerms(), HttpStatus.OK);

    }

   @GetMapping("/medical-centers")
   public ResponseEntity<List<Map<String, Object>>> searchMedicalCentersByDateTime(@RequestParam("dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") String dateTimeStr) {
       LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
       List<Map<String, Object>> medicalCentersList = termService.searchMedicalCentersByDateTime(dateTime);

       if (medicalCentersList.isEmpty()) {
           return ResponseEntity.noContent().build();
       }

       return ResponseEntity.ok(medicalCentersList);
   }

    @PostMapping("/find-term-id")
    public ResponseEntity<?> confirmTerm(@RequestParam("medicalCenterId") Integer medicalCenterId, @RequestParam("dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") String dateTimeStr) {
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);

        Long termId = termService.getTermIdByMedicalCenterAndDateTime(medicalCenterId, dateTime);

        if (termId != null) {
            return ResponseEntity.ok(termId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveTerm(@RequestParam("termId") Integer termId, @RequestParam("userId") Integer userId) {
        termService.reserveTerm(termId, userId);
        return ResponseEntity.ok("Term reserved successfully");
    }

}
