package com.ISA.ISA.service;

import com.ISA.ISA.domain.MedicalEquipment;
import com.ISA.ISA.repository.MedicalEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MedicalEquipmentServiceImpl implements MedicalEquipmentService{

    @Autowired
    private MedicalEquipmentRepository medicalEquipmentRepository;

    @Autowired
    public MedicalEquipmentServiceImpl(MedicalEquipmentRepository medicalEquipmentRepository) {
        this.medicalEquipmentRepository = medicalEquipmentRepository;
    }

    @Override
    public Map<String, MedicalEquipment> getMonthlyEquipmentCount(YearMonth yearMonth) {
        Map<String, MedicalEquipment> monthlyEquipmentCount = new HashMap<>();

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

            List<MedicalEquipment> equipmentList = medicalEquipmentRepository.findByDateBetween(startOfMonth, endOfMonth);

            MedicalEquipment totalEquipment = new MedicalEquipment();
            for (MedicalEquipment equipment : equipmentList) {
                totalEquipment.setBand_aids(totalEquipment.getBand_aids() + equipment.getBand_aids());
                totalEquipment.setDressings(totalEquipment.getDressings() + equipment.getDressings());
                totalEquipment.setNeedles(totalEquipment.getNeedles() + equipment.getNeedles());
            }

            String formattedDate = currentMonth.format(formatter);
            monthlyEquipmentCount.put(formattedDate, totalEquipment);
        }

        return monthlyEquipmentCount;
    }


    @Override
    public Map<String, MedicalEquipment> getQuarterlyEquipmentCount() {
        Map<String, MedicalEquipment> quarterlyEquipmentCount = new TreeMap<>(Comparator.comparingInt(this::getQuarterOrder));

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

            List<MedicalEquipment> equipmentList = medicalEquipmentRepository.findByDateBetween(startOfMonth, endOfMonth);

            MedicalEquipment totalEquipment = new MedicalEquipment();
            for (MedicalEquipment equipment : equipmentList) {
                totalEquipment.setBand_aids(totalEquipment.getBand_aids() + equipment.getBand_aids());
                totalEquipment.setDressings(totalEquipment.getDressings() + equipment.getDressings());
                totalEquipment.setNeedles(totalEquipment.getNeedles() + equipment.getNeedles());
            }

            String formattedDate = String.format("%s-%s (Q%d)", startYearMonth.format(formatter), endYearMonth.format(formatter), quarter);
            quarterlyEquipmentCount.put(formattedDate, totalEquipment);
        }

        return quarterlyEquipmentCount;
    }

    private int getQuarterOrder(String quarter) {
        int quarterNumber = Integer.parseInt(quarter.substring(quarter.lastIndexOf("Q") + 1, quarter.lastIndexOf(")")));
        return quarterNumber;
    }


    @Override
    public Map<Integer, String> getYearlyEquipmentCount() {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();

        Map<Integer, String> yearlyEquipmentCount = new TreeMap<>(Collections.reverseOrder());


        LocalDate startDate = LocalDate.of(currentYear, 1, 1);
        String currentYearEquipmentCount = getEquipmentCountForYear(startDate, now);
        yearlyEquipmentCount.put(currentYear, currentYearEquipmentCount);

        for (int i = 1; i <= 2; i++) {
            int year = currentYear - i;
            startDate = LocalDate.of(year, 1, 1);
            LocalDate endDate = LocalDate.of(year, 12, 31);
            String equipmentCount = getEquipmentCountForYear(startDate, endDate);
            yearlyEquipmentCount.put(year, equipmentCount);
        }

        return yearlyEquipmentCount;
    }

    private String getEquipmentCountForYear(LocalDate startDate, LocalDate endDate) {
        List<MedicalEquipment> equipmentList = medicalEquipmentRepository.findByDateBetween(startDate, endDate);

        int bandAidsCount = 0;
        int dressingsCount = 0;
        int needlesCount = 0;

        for (MedicalEquipment equipment : equipmentList) {
            bandAidsCount += equipment.getBand_aids();
            dressingsCount += equipment.getDressings();
            needlesCount += equipment.getNeedles();
        }

        return String.format("band_aids: %d, dressings: %d, needles: %d", bandAidsCount, dressingsCount, needlesCount);
    }






}




















