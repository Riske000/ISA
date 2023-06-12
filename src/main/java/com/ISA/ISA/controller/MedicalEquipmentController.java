package com.ISA.ISA.controller;

import com.ISA.ISA.domain.MedicalEquipment;
import com.ISA.ISA.service.MedicalEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("api/medical-equipment")
public class MedicalEquipmentController {
    private final MedicalEquipmentService medicalEquipmentService;

    @Autowired
    public MedicalEquipmentController(MedicalEquipmentService medicalEquipmentService) {
        this.medicalEquipmentService = medicalEquipmentService;
    }

    @GetMapping("/monthly-equipment-count")
    public ResponseEntity<Map<String, MedicalEquipment>> getMonthlyEquipmentCount() {
        LocalDate now = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(now);

        Map<String, MedicalEquipment> monthlyEquipmentCount = medicalEquipmentService.getMonthlyEquipmentCount(currentYearMonth);

        return ResponseEntity.ok(monthlyEquipmentCount);
    }

    @GetMapping("/quarterly-equipment-count")
    public ResponseEntity<Map<String, MedicalEquipment>> getQuarterlyEquipmentCount() {
        Map<String, MedicalEquipment> quarterlyEquipmentCount = medicalEquipmentService.getQuarterlyEquipmentCount();
        return ResponseEntity.ok(quarterlyEquipmentCount);
    }

    @GetMapping("/yearly-equipment-count")
    public ResponseEntity<Map<Integer, String>> getYearlyEquipmentCount() {
        Map<Integer, String> equipmentCount = medicalEquipmentService.getYearlyEquipmentCount();
        return ResponseEntity.ok(equipmentCount);
    }





}