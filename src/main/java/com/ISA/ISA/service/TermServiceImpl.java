package com.ISA.ISA.service;

import com.ISA.ISA.domain.*;
import com.ISA.ISA.domain.DTO.TermDTO;
import com.ISA.ISA.domain.enums.BloodType;
import com.ISA.ISA.domain.enums.StatusOfTerm;
import com.ISA.ISA.repository.*;
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
    @Autowired
    private SuppliesRepository suppliesRepository;
    @Autowired
    private BloodRepository bloodRepository;
    @Autowired
    private ReviewRepository reviewRepository;



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

        LocalTime termTime = LocalTime.of(termDate.getHours(), termDate.getMinutes());
        LocalTime startTime = medicalCenter.get().getStartTime();
        LocalTime endTime = medicalCenter.get().getEndTime();

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
    public void reserveTerm(Integer termId, Integer userId, Integer questionnaireId) {
        Optional<Term> termOptional = termRepository.findById(termId);
        if (termOptional.isPresent()) {
            Term term = termOptional.get();
            User user = new User();
            user.setId(userId);
            term.setUser(user);
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setId(questionnaireId);
            term.setQuestionnaire(questionnaire);
            term.setStatusOfTerm(StatusOfTerm.Taken);

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
    public Boolean hasTermsByUserAndMedicalCenter(User user, MedicalCenter medicalCenter) {
        List<Term> terms = termRepository.findByUserAndMedicalCenter(user, medicalCenter);

        for (Term term : terms) {
            if (term.getStatusOfTerm() == StatusOfTerm.Taken) {
                LocalDateTime termDateTime = term.getDateOfTerm().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
                LocalDateTime currentDateTime = LocalDateTime.now();

                if (termDateTime.isBefore(currentDateTime)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public List<Term> getTermsForMedicalCenter(Integer medicalCenterId) {
        List<Term> terms = termRepository.findByMedicalCenterIdAndStatusOfTerm(medicalCenterId, StatusOfTerm.Taken);
        List<Term> filteredTerms = new ArrayList<>();

        for (Term term : terms) {
            if (term.getStatusOfTerm() == StatusOfTerm.Taken) {
                LocalDateTime termDateTime = term.getDateOfTerm().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
                LocalDateTime currentDateTime = LocalDateTime.now();

                if (termDateTime.isBefore(currentDateTime)) {
                    filteredTerms.add(term);
                }
            }
        }

        return filteredTerms;
    }

    @Override
    public Term getTermById(Integer termId){
        Optional<Term> optionalTerm = termRepository.findById(termId);
        return optionalTerm.orElse(null);
    }

    @Override
    public void didNotShowUp(Integer termId) {
        Optional<Term> optionalTerm = termRepository.findById(termId);
        if (optionalTerm.isPresent()) {
            Term term = optionalTerm.get();

            LocalDateTime termDateTime = term.getDateOfTerm().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
            LocalDateTime currentDateTime = LocalDateTime.now();

            if(termDateTime.isAfter(currentDateTime)){
                throw new IllegalStateException("Nije moguće ne pojaviti se ako je nesto u buducnosti.");
            }

            if (term.getStatusOfTerm() == StatusOfTerm.Taken) {
                Optional<Questionnaire> optionalQuestionnaire = questionnaireRepository.findById(term.getQuestionnaire().getId());
                if (optionalQuestionnaire.isPresent()) {
                    Questionnaire questionnaire = optionalQuestionnaire.get();
                    if (questionnaire.isDeleted() == false) {
                        questionnaire.setDeleted(true);
                        User user = term.getUser();
                        user.setPenalties(user.getPenalties() + 1);

                        userRepository.save(user);
                        questionnaireRepository.save(questionnaire);
                    }
                }
            }
        }
    }

    @Override
    public void canceledByAdmin(Integer termId) {
        Optional<Term> optionalTerm = termRepository.findById(termId);

        if (optionalTerm.isPresent() && optionalTerm.get().getStatusOfTerm() == StatusOfTerm.Taken) {
            Questionnaire questionnaire = optionalTerm.get().getQuestionnaire();
            if (questionnaire != null) {
                if (questionnaire.isQuestion1() || questionnaire.isQuestion2() ||
                        questionnaire.isQuestion3() || questionnaire.isQuestion4() ||
                        questionnaire.isQuestion5() || questionnaire.isQuestion6() ||
                        questionnaire.isQuestion7() || questionnaire.isQuestion8() ||
                        questionnaire.isQuestion9() || questionnaire.isQuestion10() ||
                        questionnaire.isQuestion11() || questionnaire.isQuestion12() ||
                        questionnaire.isQuestion13() || questionnaire.isQuestion14() ||
                        questionnaire.isQuestion15()) {

                    Term term = optionalTerm.get();

                    term.setStatusOfTerm(StatusOfTerm.Canceled);
                    questionnaire.setDeleted(true);

                    termRepository.save(term);
                    questionnaireRepository.save(questionnaire);
                }
            }
        }
    }


    @Override
    public void adminReview(Integer termId, String description, Integer needleNo, Integer cottonWoolNo, Integer alcoholNo, BloodType bloodType, double deciliters){
        Optional<Term> optionalTerm = termRepository.findById(termId);
        Term term = optionalTerm.get();
        LocalDateTime termDateTime = term.getDateOfTerm().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
        LocalDateTime currentDateTime = LocalDateTime.now();
        if(term != null && term.getStatusOfTerm() == StatusOfTerm.Taken && termDateTime.isBefore(currentDateTime)){
            term.setStatusOfTerm(StatusOfTerm.Done);
            term.setDescription(description);
            Questionnaire questionnaire = term.getQuestionnaire();
            if(questionnaire != null && !questionnaire.isDeleted()){
                term.getQuestionnaire().setDeleted(true);

                MedicalCenter medicalCenter = term.getMedicalCenter();
                if(medicalCenter != null){
                    Supplies supplies = suppliesRepository.findByMedicalCenter(medicalCenter);
                    supplies.setNeedle(supplies.getNeedle() - needleNo);
                    supplies.setCottonWool(supplies.getCottonWool() - cottonWoolNo);
                    supplies.setAlcohol(supplies.getAlcohol() - alcoholNo);
                    suppliesRepository.save(supplies);

                    Blood blood = bloodRepository.findByBloodTypeAndMedicalCenter(bloodType, medicalCenter);
                    blood.setDeciliters(blood.getDeciliters() + deciliters);
                    bloodRepository.save(blood);

                    Review review = new Review();
                    review.setTerm(term);
                    review.setDeciliters(deciliters);
                    review.setAlcoholNo(alcoholNo);
                    review.setNeedleNo(needleNo);
                    review.setCottonWoolNo(cottonWoolNo);
                    review.setBloodType(bloodType);
                    reviewRepository.save(review);
                }
                questionnaireRepository.save(questionnaire);
            }
            termRepository.save(term);
        }
    }


}
