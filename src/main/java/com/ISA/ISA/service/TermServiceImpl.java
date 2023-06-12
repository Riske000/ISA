package com.ISA.ISA.service;

import com.ISA.ISA.domain.*;
import com.ISA.ISA.domain.DTO.TermDTO;
import com.ISA.ISA.domain.enums.StatusOfTerm;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.repository.QuestionnaireRepository;
import com.ISA.ISA.repository.TermRepository;
import com.ISA.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.*;
import java.util.*;

@Service
public class TermServiceImpl implements TermService{
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MedicalCenterRepository medicalCenterRepository;
    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BloodGivingService bloodGivingService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Override
    public Term add(TermDTO termDTO){
        Term term = TermDTO.convertBack(termDTO);
        Optional<MedicalCenter> medicalCenter = medicalCenterRepository.findById(termDTO.getMedicalCenterId());
        if(medicalCenter.isEmpty()){
            term.setUser(null);
        }
        term.setMedicalCenter(medicalCenter.get());
        Date termDate = term.getDateOfTerm();
        Date currentDate = new Date();
        if (termDate.before(currentDate)) {
            throw new IllegalArgumentException("Date of term must be in future.");
        }

        LocalDateTime termDateTime = LocalDateTime.ofInstant(termDate.toInstant(), ZoneId.systemDefault());
        LocalTime termTime = termDateTime.toLocalTime();
        LocalTime startTime = medicalCenter.get().getStartTime().plusHours(2);
        LocalTime endTime = medicalCenter.get().getEndTime().plusHours(2);

        if (termTime.isBefore(startTime) || termTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Term time must be within the working hours of the medical center.");
        }

        term = termRepository.save(term);
        return term;
    }

    @Transactional
    @Override
    public Term edit(TermDTO termDTO){
        Optional<Term> term = termRepository.findById(termDTO.getId());
        if (term.isEmpty()){
            return null;
        }

        term.get().setDateOfTerm(termDTO.getDateOfTerm());
        term.get().setDuration(termDTO.getDuration());
        term.get().setStatusOfTerm(term.get().getStatusOfTerm());
        Optional<MedicalCenter> medicalCenter = medicalCenterRepository.findById(termDTO.getMedicalCenterId());
        if (medicalCenter.isPresent()){
            term.get().setMedicalCenter(medicalCenter.get());
        }
        Optional<User> user = userRepository.findById(termDTO.getUserId());
        if (user.isPresent()){
            term.get().setUser(user.get());
        }
        return termRepository.save(term.get());
    }
    @Transactional
    @Override
    public void delete(int id){
        Optional<Term> term = termRepository.findById(id);
        if (term.isPresent()) {
            term.get().setDeleted(true);
        }
    }
    @Transactional
    @Override
    public Optional<Term> findById(int id){
        Optional<Term> term = termRepository.findById(id);
        return term;
    }

    @Override
    public List<Term> getAll(){
        return termRepository.findAll();
    }

    @Override
    public List<Term >getAllForSelectedCenter(int medicalCenterId){
        Optional<MedicalCenter> medicalCenter = medicalCenterRepository.findById(medicalCenterId);
        List<Term> termsToShow = new ArrayList<>();
        List<Term> allTerms = termRepository.findAll();
        Date date = new Date();

        if (medicalCenter.isEmpty()){
            return null;
        }

        List<Term> terms = termRepository.findAllByMedicalCenterAndStatusOfTerm(medicalCenter.get(), StatusOfTerm.Free);

        for(Term t : terms){
            if(t.getDateOfTerm().after(date)){
                termsToShow.add(t);
            }
        }
        return termsToShow;
    }
    @Transactional
    @Override
    public Term reserveTerm(int termId, int userId){
        Optional<User> user = userRepository.findById(userId);
        Questionnaire questionnaire = questionnaireRepository.findOneByUserAndDeleted(user.get(), false);
        Optional<Term> term = termRepository.findById(termId);
        MedicalCenter medicalCenter = term.get().getMedicalCenter();

        String qrCode;
        Date date = new Date();
        if(user.get().getPenalties() == 3){
            return null;
        }

        if(!bloodGivingService.CanUserGiveBlood(userId)){
            return null;
        }

        if(term.get().getStatusOfTerm().equals(StatusOfTerm.Taken)){
            return null;
        }

        if(questionnaire == null){
            return null;
        }
        List<Term> canUserReserve = termRepository.findAllByUserAndMedicalCenter(user.get(), medicalCenter);

        for (Term t : canUserReserve) {
            if (t != null && t.getDateOfTerm().after(date)) {
                return null;
            }
        }
        try{
            emailService.sendQRCode(user.get().getEmail(), term.get());
        } catch (Exception e){
            return null;
        }


        term.get().setUser(user.get());
        term.get().setStatusOfTerm(StatusOfTerm.Taken);

        Term saved = termRepository.save(term.get());
        return saved;

    }

    @Transactional
    @Override
    public Term cancelTerm(int termId, int userId){
        Optional<Term> term = termRepository.findById(termId);

        if(term.isEmpty()){
            return null;
        }

        Date date = new Date();
        long millis = term.get().getDateOfTerm().getTime();
        long millisPerDay = 24 * 60 * 60 * 1000;
        long newMillis = millis - millisPerDay;
        Date newDate = new Date(newMillis);

        if(newDate.before(date)){
            return null;
        }

        term.get().setStatusOfTerm(StatusOfTerm.Free);
        term.get().setUser(null);

        return termRepository.save(term.get());
    }

    public List<Term> getReservedTermForUser(int userId){
        List<Term> terms;
        List<Term> termsToShow = new ArrayList<>();
        Date now = new Date();
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            return null;
        }

        terms = termRepository.findAllByUser(user.get());

        for (Term term : terms){
            if (term.getDateOfTerm().after(now)){
                termsToShow.add(term);
            }
        }
        return termsToShow;
    }

    @Override
    public List<Term> getAllFreeTerms() {
        return termRepository.findByStatusOfTerm(StatusOfTerm.Free);
    }

    @Override
    public List<Map<String, Object>> searchMedicalCentersByDateTime(LocalDateTime dateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (dateTime.isBefore(currentDateTime)) {
            throw new IllegalArgumentException("Date cannot be in the past.");
        }
        Date date = Date.from(dateTime.toInstant(ZoneOffset.UTC));

        List<Term> terms = termRepository.findByDateOfTerm(date);
        List<Map<String, Object>> medicalCentersList = new ArrayList<>();

        for (Term term : terms) {
            if (term.getStatusOfTerm() == StatusOfTerm.Free) {
                MedicalCenter medicalCenter = term.getMedicalCenter();
                int termId = term.getId();

                Map<String, Object> medicalCenterMap = new HashMap<>();
                medicalCenterMap.put("medicalCenter", medicalCenter);
                medicalCenterMap.put("termId", termId);

                medicalCentersList.add(medicalCenterMap);
            }
        }

       // medicalCentersList.sort(Comparator.comparing(mc -> ((MedicalCenter) mc.get("medicalCenter")).getAverageRating()));


        return medicalCentersList;
    }

    @Override
    public Long getTermIdByMedicalCenterAndDateTime(Integer medicalCenterId, LocalDateTime dateTime) {
        Date date = Date.from(dateTime.toInstant(ZoneOffset.UTC));
        Term term = termRepository.findByMedicalCenterIdAndDateOfTerm(medicalCenterId, date);
        if (term != null) {
            return (long) term.getId();
        } else {
            return null;
        }
    }

    @Override
    public void reserveTerm(Integer termId, Integer userId) {
        Optional<Term> termOptional = termRepository.findById(termId);
        if (termOptional.isPresent()) {
            Term term = termOptional.get();

            if (term.getStatusOfTerm() == StatusOfTerm.Taken) {
                throw new IllegalArgumentException("Term is already taken.");
            }

            User user = new User();
            user.setId(userId);
            term.setUser(user);
            term.setStatusOfTerm(StatusOfTerm.Taken);

            Date termDate = term.getDateOfTerm();
            Date currentDate = new Date();
            if (termDate.before(currentDate)) {
                throw new IllegalArgumentException("Term date must be in the future.");
            }

            Questionnaire lastQuestionnaire = questionnaireService.getLastForUser(userId);
            if (lastQuestionnaire == null) {
                throw new IllegalArgumentException("User must fill out the questionnaire before reserving a term.");
            }

            termRepository.save(term);
        } else {
            throw new IllegalArgumentException("Term with ID " + termId + " not found");
        }
    }

    @Override
    public int getTermCountByMonthForMedicalCenter(YearMonth yearMonth, int medicalCenterId) {
        YearMonth currentYearMonth = YearMonth.now();
        int currentDay = LocalDate.now().getDayOfMonth();

        if (yearMonth.isAfter(currentYearMonth)) {
            return 0;
        }

        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth;

        if (yearMonth.equals(currentYearMonth)) {
            endOfMonth = LocalDateTime.now();
        } else {
            endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        }

        Date startDate = Date.from(startOfMonth.toInstant(ZoneOffset.UTC));
        Date endDate = Date.from(endOfMonth.toInstant(ZoneOffset.UTC));

        return termRepository.countByDateOfTermBetweenAndMedicalCenterId(startDate, endDate, medicalCenterId);
    }


    @Override
    public Map<String, Integer> getTermCountsByQuarters(Integer year, int medicalCenterId) {
        if (year == null) {
            year = Year.now().getValue();
        }

        Map<String, Integer> termCounts = new HashMap<>();

        for (int quarter = 1; quarter <= 4; quarter++) {
            int startMonth = (quarter - 1) * 3 + 1;
            int endMonth = quarter * 3;

            YearMonth startYearMonth = YearMonth.of(year, startMonth);
            YearMonth endYearMonth = YearMonth.of(year, endMonth);

            if (endYearMonth.isAfter(YearMonth.now())) {
                endYearMonth = YearMonth.now();
            }

            int termCount = termRepository.countByDateOfTermBetweenAndMedicalCenterId(
                    getStartOfMonth(startYearMonth), getEndOfMonth(endYearMonth), medicalCenterId);

            String quarterKey = "Q" + quarter;
            termCounts.put(quarterKey, termCount);
        }

        return termCounts;
    }

    private Date getEndOfMonth(YearMonth yearMonth) {
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return Date.from(endOfMonth.toInstant(ZoneOffset.UTC));
    }

    private Date getStartOfMonth(YearMonth yearMonth) {
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        return Date.from(startOfMonth.toInstant(ZoneOffset.UTC));
    }


    @Override
    public Map<Integer, Integer> getTermCountsByYears(Integer medicalCenterId) {
        Year currentYear = Year.now();
        int currentYearValue = currentYear.getValue();
        int previousYearValue = currentYearValue - 1;
        int twoYearsAgoValue = currentYearValue - 2;

        Map<Integer, Integer> termCounts = new HashMap<>();

        int currentYearTermCount = getTermCountByYear(currentYear, medicalCenterId);
        termCounts.put(currentYearValue, currentYearTermCount);

        int previousYearTermCount = getTermCountByYear(currentYear.minusYears(1), medicalCenterId);
        termCounts.put(previousYearValue, previousYearTermCount);

        int twoYearsAgoTermCount = getTermCountByYear(currentYear.minusYears(2), medicalCenterId);
        termCounts.put(twoYearsAgoValue, twoYearsAgoTermCount);

        return termCounts;
    }

    private int getTermCountByYear(Year year, int medicalCenterId) {
        LocalDateTime startOfYear = LocalDateTime.of(year.getValue(), 1, 1, 0, 0);
        LocalDateTime endOfYear = year.atMonth(YearMonth.now().getMonth()).atEndOfMonth().atTime(23, 59, 59);

        Date startDate = Date.from(startOfYear.toInstant(ZoneOffset.UTC));
        Date endDate = Date.from(endOfYear.toInstant(ZoneOffset.UTC));

        return termRepository.countByDateOfTermBetweenAndMedicalCenterId(startDate, endDate, medicalCenterId);
    }








}
