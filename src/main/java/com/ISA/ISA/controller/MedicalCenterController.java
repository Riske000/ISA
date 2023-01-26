package com.ISA.ISA.controller;


import com.ISA.ISA.domain.DTO.MedicalCenterDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.service.MedicalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/medical")
public class MedicalCenterController {
    @Autowired
    private MedicalCenterService medicalCenterService;

    @PostMapping(path = "/create")
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

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        medicalCenterService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<MedicalCenter> medicalCenter = medicalCenterService.findById(id);

        if(medicalCenter.isEmpty()){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(medicalCenter.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll(){
        List<MedicalCenter> medicalCenters = medicalCenterService.getAll();

        if(medicalCenters == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(medicalCenters, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllSorted/")
    public ResponseEntity<?> getAllSorted(@RequestParam String field, @RequestParam int pageNo,
                                          @RequestParam int pageSize, @RequestParam String sortMode){
        int page = pageNo - 1;
        Page<MedicalCenter> medicalCenters = medicalCenterService.getSorted(field, page, pageSize, sortMode);

        if(medicalCenters == null){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>(medicalCenters, HttpStatus.OK);
    }

    @GetMapping(value = "/getNumberOfCenters/")
    public ResponseEntity<?> getNumberOfCenters(){
        int number = medicalCenterService.getNumberOfCenters();

        return new ResponseEntity<>(number, HttpStatus.OK);
    }
}
