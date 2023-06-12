package com.ISA.ISA.service;

import com.ISA.ISA.domain.BloodSupply;
import com.ISA.ISA.repository.BloodSupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BloodSupplyServiceImpl implements BloodSupplyService {

    @Autowired
    private BloodSupplyRepository bloodSupplyRepository;

    @Autowired
    public BloodSupplyServiceImpl(BloodSupplyRepository bloodSupplyRepository) {
        this.bloodSupplyRepository = bloodSupplyRepository;
    }

    @Override
    public Map<String, BloodSupply> getMonthlyBloodSupplyCount(YearMonth yearMonth) {
        Map<String, BloodSupply> monthlyBloodSupplyCount = new HashMap<>();

        YearMonth now = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        LocalDate currentDate = LocalDate.now();
        for (int month = 1; month <= now.getMonthValue(); month++) {
            YearMonth currentMonth = YearMonth.of(now.getYear(), month);
            LocalDate startOfMonth = currentMonth.atDay(1);
            LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

            if (currentMonth.equals(now)) {
                endOfMonth = currentDate;
            }

            List<BloodSupply> bloodSupplyList = bloodSupplyRepository.findByDateBetween(startOfMonth, endOfMonth);

            BloodSupply totalBloodSupply = new BloodSupply();
            for (BloodSupply bloodSupply : bloodSupplyList) {
                totalBloodSupply.setGiven(totalBloodSupply.getGiven() + bloodSupply.getGiven());
                totalBloodSupply.setTaken(totalBloodSupply.getTaken() + bloodSupply.getTaken());
            }

            String formattedDate = currentMonth.format(formatter);
            monthlyBloodSupplyCount.put(formattedDate, totalBloodSupply);
        }

        return monthlyBloodSupplyCount;
    }

    @Override
    public Map<String, BloodSupply> getQuarterlyBloodCount() {
        Map<String, BloodSupply> quarterlyBloodCount = new TreeMap<>(Comparator.comparingInt(this::getQuarterOrder));

        YearMonth now = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");

        int startQuarter = 1;
        int endQuarter = (now.getMonthValue() - 1) / 3 + 1;

        for (int quarter = startQuarter; quarter <= endQuarter; quarter++) {
            int startMonth = (quarter - 1) * 3 + 1;
            int endMonth = startMonth + 2;

            if (endMonth > now.getMonthValue()) {
                endMonth = now.getMonthValue();
            }

            YearMonth startYearMonth = YearMonth.of(now.getYear(), startMonth);
            YearMonth endYearMonth = YearMonth.of(now.getYear(), endMonth);
            LocalDate startOfMonth = startYearMonth.atDay(1);
            LocalDate endOfMonth = endYearMonth.atEndOfMonth();

            if (endYearMonth.equals(now)) {
                endOfMonth = LocalDate.now();
            }

            List<BloodSupply> bloodSupplyList = bloodSupplyRepository.findByDateBetween(startOfMonth, endOfMonth);

            BloodSupply totalBloodSupply = new BloodSupply();
            for (BloodSupply bloodSupply : bloodSupplyList) {
                totalBloodSupply.setGiven(totalBloodSupply.getGiven() + bloodSupply.getGiven());
                totalBloodSupply.setTaken(totalBloodSupply.getTaken() + bloodSupply.getTaken());
            }

            String formattedDate = String.format("%s-%s (Q%d)", startYearMonth.format(formatter), endYearMonth.format(formatter), quarter);
            quarterlyBloodCount.put(formattedDate, totalBloodSupply);
        }

        return quarterlyBloodCount;
    }

    private int getQuarterOrder(String quarter) {
        int quarterNumber = Integer.parseInt(quarter.substring(quarter.lastIndexOf("Q") + 1, quarter.lastIndexOf(")")));
        return quarterNumber;
    }

    @Override
    public Map<Integer, String> getYearlyBloodCount() {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();

        Map<Integer, String> yearlyBloodCount = new TreeMap<>(Collections.reverseOrder());

        LocalDate startDate = LocalDate.of(currentYear, 1, 1);
        String currentYearBloodCount = getBloodCountForYear(startDate, now);
        yearlyBloodCount.put(currentYear, currentYearBloodCount);

        for (int i = 1; i <= 2; i++) {
            int year = currentYear - i;
            startDate = LocalDate.of(year, 1, 1);
            LocalDate endDate = LocalDate.of(year, 12, 31);
            String bloodCount = getBloodCountForYear(startDate, endDate);
            yearlyBloodCount.put(year, bloodCount);
        }

        return yearlyBloodCount;
    }

    private String getBloodCountForYear(LocalDate startDate, LocalDate endDate) {
        List<BloodSupply> bloodSupplyList = bloodSupplyRepository.findByDateBetween(startDate, endDate);

        double givenCount = 0.0;
        double takenCount = 0.0;

        for (BloodSupply bloodSupply : bloodSupplyList) {
            givenCount += bloodSupply.getGiven();
            takenCount += bloodSupply.getTaken();
        }

        return String.format("given: %f, taken: %f", givenCount, takenCount);
    }



}
