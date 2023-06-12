package com.ISA.ISA.service;

import com.ISA.ISA.domain.BloodSupply;

import java.time.YearMonth;
import java.util.Map;

public interface BloodSupplyService {
    Map<String, BloodSupply> getMonthlyBloodSupplyCount(YearMonth yearMonth);

    Map<String, BloodSupply> getQuarterlyBloodCount();

    Map<Integer, String> getYearlyBloodCount();
}
