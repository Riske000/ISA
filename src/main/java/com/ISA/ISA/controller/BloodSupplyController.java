package com.ISA.ISA.controller;

import com.ISA.ISA.domain.BloodSupply;
import com.ISA.ISA.domain.MedicalEquipment;
import com.ISA.ISA.service.BloodSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("api/blood-supply")
public class BloodSupplyController {

    @Autowired
    private BloodSupplyService bloodSupplyService;

    @Autowired
    public BloodSupplyController(BloodSupplyService bloodSupplyService) {
        this.bloodSupplyService = bloodSupplyService;
    }

    @GetMapping("/monthly-blood-count")
    public ResponseEntity<Map<String, BloodSupply>> getMonthlyBloodSupplyCount() {
        LocalDate now = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(now);

        Map<String, BloodSupply> monthlyBloodSupplyCount = bloodSupplyService.getMonthlyBloodSupplyCount(currentYearMonth);

        return ResponseEntity.ok(monthlyBloodSupplyCount);
    }

    @GetMapping("/quarterly-blood-count")
    public ResponseEntity<Map<String, BloodSupply>> getQuarterlyBloodSupplyCount() {
        Map<String, BloodSupply> quarterlyBloodSupplyCount = bloodSupplyService.getQuarterlyBloodCount();
        return ResponseEntity.ok(quarterlyBloodSupplyCount);
    }

    @GetMapping("/yearly-blood-count")
    public ResponseEntity<Map<Integer, String>> getYearlyBloodSupplyCount() {
        Map<Integer, String> bloodSupplyCount = bloodSupplyService.getYearlyBloodCount();
        return ResponseEntity.ok(bloodSupplyCount);
    }
}
