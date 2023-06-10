package com.ISA.ISA.controller;


import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.service.RateCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/rate-center")
public class RateCenterController {
    @Autowired
    private  RateCenterService rateCenterService;




    @PostMapping("/rate")
    public ResponseEntity<String> rateMedicalCenter(
            @RequestParam Integer userId,
            @RequestParam Integer medicalCenterId,
            @RequestParam int rating
    ) {
        try {
            rateCenterService.rateMedicalCenter(userId, medicalCenterId, rating);
            return ResponseEntity.ok("Centar je uspešno ocenjen.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update-rating")
    public ResponseEntity<String> updateRating(
            @RequestParam Integer userId,
            @RequestParam Integer medicalCenterId,
            @RequestParam int newRating
    ) {
        try {
            rateCenterService.updateRating(userId, medicalCenterId, newRating);
            return ResponseEntity.ok("Ocena je uspešno ažurirana.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
