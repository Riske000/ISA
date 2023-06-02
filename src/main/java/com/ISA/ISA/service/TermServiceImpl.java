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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Term add(TermDTO termDTO){
        Term term = TermDTO.convertBack(termDTO);
        Optional<MedicalCenter> medicalCenter = medicalCenterRepository.findById(termDTO.getMedicalCenterId());
        if(medicalCenter.isEmpty()){
            term.setUser(null);
        }
        term.setMedicalCenter(medicalCenter.get());
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

}
