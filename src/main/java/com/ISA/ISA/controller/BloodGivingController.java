package com.ISA.ISA.controller;

import com.ISA.ISA.domain.BloodGiving;
import com.ISA.ISA.service.BloodGivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bloodGiving")
public class BloodGivingController {
    @Autowired
    private BloodGivingService bloodGivingService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllForUser(@PathVariable int userId, @RequestParam String field, @RequestParam int pageNo,
                                           @RequestParam int pageSize, @RequestParam String sortMode){
        Pageable paging;

        if(sortMode.equals("Ascending")) {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, field));
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, field));
        }

        Page<BloodGiving> bloodGivings = bloodGivingService.GetAllForUser(userId, paging);

        if(bloodGivings == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(bloodGivings, HttpStatus.OK);

    }
}
