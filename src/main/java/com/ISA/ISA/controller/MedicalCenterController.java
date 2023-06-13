package com.ISA.ISA.controller;


import com.ISA.ISA.domain.Blood;
import com.ISA.ISA.domain.DTO.MedicalCenterDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.repository.BloodRepository;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.service.MedicalCenterService;
import com.ISA.ISA.service.RateCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/medical")
public class MedicalCenterController {
    @Autowired
    private MedicalCenterService medicalCenterService;

    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

    @Autowired
    private BloodRepository bloodRepository;




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

    @CrossOrigin
    @GetMapping(value = "/getAllSorted")
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

    @GetMapping("/terms/{medicalCenterId}")
    public ResponseEntity<List<Term>> getFreeTermsByMedicalCenterId(@PathVariable("medicalCenterId") Integer medicalCenterId) {
        List<Term> terms = medicalCenterService.getTermsByMedicalCenterId(medicalCenterId);
        return new ResponseEntity<>(terms, HttpStatus.OK);
    }

    @PutMapping("/edit-working-hours/{id}")
    public ResponseEntity<?> updateWorkingHours(
            @PathVariable Integer id,
            @RequestBody MedicalCenterDTO medicalCenterDTO
    ) {
        LocalTime startTime = medicalCenterDTO.getStartTime();
        LocalTime endTime = medicalCenterDTO.getEndTime();

        MedicalCenterDTO updatedMedicalCenter = medicalCenterService.updateWorkingHours(id, startTime, endTime);
        return ResponseEntity.ok(updatedMedicalCenter);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MedicalCenter>> searchMedicalCenters(
            @RequestParam(required = false) String centerName,
            @RequestParam(required = false) String adress
    ) {
        List<MedicalCenter> medicalCenters;
        if (centerName != null && adress != null) {
            medicalCenters = medicalCenterService.searchMedicalCentersByCenterNameAndAdressIgnoreCase(centerName, adress);
        } else if (centerName != null) {
            medicalCenters = medicalCenterService.searchMedicalCentersByCenterNameIgnoreCase(centerName);
        } else if (adress != null) {
            medicalCenters = medicalCenterService.searchMedicalCentersByAdressIgnoreCase(adress);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(medicalCenters);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MedicalCenter>> filterMedicalCentersByRating(
            @RequestParam(required = false) Double minRating
    ) {
        List<MedicalCenter> filteredCenters;
        if (minRating != null) {
            filteredCenters = medicalCenterService.filterMedicalCentersByRating(minRating);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(filteredCenters);
    }

    @PutMapping("/edit-info/{id}")
    public ResponseEntity<?> updateCenterInfo(
            @PathVariable Integer id,
            @RequestBody MedicalCenterDTO medicalCenterDTO
    ) {
        String centerName = medicalCenterDTO.getCenterName();
        String address = medicalCenterDTO.getAddress();
        String description = medicalCenterDTO.getDescription();
        Double averageRating = medicalCenterDTO.getAverageRating();

        MedicalCenterDTO updatedMedicalCenter = medicalCenterService.updateCenterInfo(id, centerName, address, description, averageRating);
        return ResponseEntity.ok(updatedMedicalCenter);
    }

    @GetMapping("/medical-center/{medicalCenterId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Integer medicalCenterId) {
        double averageRating = medicalCenterService.averageRating(medicalCenterId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/users/visited/{medicalCenterId}")
    public ResponseEntity<List<User>> getUsersWhoVisited(@PathVariable Integer medicalCenterId) {
        List<User> visitedUsers = medicalCenterService.usersWhoVisited(medicalCenterId);

        if (visitedUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(visitedUsers);
        }
    }

    @GetMapping("/blood/{medicalCenterId}")
    public ResponseEntity<List<Blood>>  getBloodByMedicalCenter(@PathVariable Integer medicalCenterId) {
        MedicalCenter medicalCenter = medicalCenterRepository.findById(medicalCenterId).get();
        List<Blood> bloods = bloodRepository.findByMedicalCenter(medicalCenter);

        if(bloods.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(bloods);
        }
    }

}
