package com.ISA.ISA.controller;

import com.ISA.ISA.domain.DTO.ReserveTermDTO;
import com.ISA.ISA.domain.DTO.TermDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.domain.enums.BloodType;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

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
    public ResponseEntity<String> reserveTerm(@RequestParam("termId") Integer termId, @RequestParam("userId") Integer userId,
                                              @RequestParam("questionnaireId") Integer questionnaireId) {
        termService.reserveTerm(termId, userId, questionnaireId);
        return ResponseEntity.ok("Term reserved successfully");
    }

    @GetMapping("/terms/user/{userId}/medical-center/{medicalCenterId}")
    public Boolean hasTermsByUserAndMedicalCenter(@PathVariable Integer userId, @PathVariable Integer medicalCenterId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<MedicalCenter> medicalCenter = medicalCenterRepository.findById(medicalCenterId);
        if (user.isEmpty() || medicalCenter.isEmpty()) {
            return false;
        }

        return termService.hasTermsByUserAndMedicalCenter(user.get(), medicalCenter.get());
    }

    @GetMapping("/medical-center/{medicalCenterId}/past-taken-terms")
    public ResponseEntity<List<Term>> getPastTakenTermsForMedicalCenter(@PathVariable Integer medicalCenterId) {
        List<Term> terms = termService.getTermsForMedicalCenter(medicalCenterId);
        return ResponseEntity.ok(terms);
    }


    @GetMapping("/getTerm/{termId}")
    public ResponseEntity<Term> getTermById(@PathVariable Integer termId) {
        Term term = termService.getTermById(termId);
        if (term != null) {
            return ResponseEntity.ok(term);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/cancel-penalty/{termId}")
    public ResponseEntity<String> didNotShowUp(@PathVariable Integer termId) {
        termService.didNotShowUp(termId);
        return ResponseEntity.ok("Term canceled.");
    }

    @PutMapping("/cancel-admin/{termId}")
    public ResponseEntity<String> cancelTermByAdmin(@PathVariable Integer termId) {
        termService.canceledByAdmin(termId);
        return ResponseEntity.ok("Term successfully canceled by admin.");
    }

    @PostMapping("/review")
    public void adminReview(@RequestParam("termId") Integer termId,
                            @RequestParam("description") String description,
                            @RequestParam("needleNo") Integer needleNo,
                            @RequestParam("cottonWoolNo") Integer cottonWoolNo,
                            @RequestParam("alcoholNo") Integer alcoholNo,
                            @RequestParam("bloodType") BloodType bloodType,
                            @RequestParam("deciliters") double deciliters) {
        termService.adminReview(termId, description, needleNo, cottonWoolNo, alcoholNo, bloodType, deciliters);
    }

}
