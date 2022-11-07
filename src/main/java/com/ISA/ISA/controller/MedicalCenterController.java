package com.ISA.ISA.controller;


import com.ISA.ISA.domain.DTO.MedicalCenterDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.service.MedicalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/loads")
public class MedicalCenterController {
    @Autowired
    private MedicalCenterService medicalCenterService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody MedicalCenterDTO medicalCenterDTO){
        MedicalCenter medicalCenter = medicalCenterService.add(medicalCenterDTO);

        if(medicalCenter == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(medicalCenter, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody MedicalCenterDTO medicalCenterDTO){
        MedicalCenter medicalCenter = medicalCenterService.edit(medicalCenterDTO);

        if(medicalCenter == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(medicalCenter, HttpStatus.OK);
    }

    @DeleteMapping(path = "{/id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        medicalCenterService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "{/id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        MedicalCenter medicalCenter = medicalCenterService.findById(id);

        if(medicalCenter == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(medicalCenter, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<MedicalCenter> medicalCenters = medicalCenterService.getAll();

        if(medicalCenters == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(medicalCenters, HttpStatus.OK);
    }
}
