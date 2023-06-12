package com.ISA.ISA.service;

import com.ISA.ISA.domain.MedicalEquipment;

import java.time.YearMonth;
import java.util.Map;

public interface MedicalEquipmentService {

    Map<String, MedicalEquipment> getMonthlyEquipmentCount(YearMonth yearMonth);

    Map<String, MedicalEquipment> getQuarterlyEquipmentCount();

    Map<Integer, String> getYearlyEquipmentCount();
}
