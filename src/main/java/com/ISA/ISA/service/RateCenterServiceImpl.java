package com.ISA.ISA.service;

import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.RateCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.domain.enums.StatusOfTerm;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.repository.RateCenterRepository;
import com.ISA.ISA.repository.TermRepository;
import com.ISA.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class RateCenterServiceImpl implements RateCenterService{

    @Autowired
    private RateCenterRepository rateCenterRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MedicalCenterRepository medicalCenterRepository;
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private TermService termService;

    @Override
    public void rateMedicalCenter(Integer userId, Integer medicalCenterId, int rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Ocena mora biti između 0 i 5.");
        }

        boolean hasVisitedCenter = termService.hasTermsByUserAndMedicalCenter(userRepository.findById(userId).get(), medicalCenterRepository.findById(medicalCenterId).get());

        if (!hasVisitedCenter) {
            throw new IllegalArgumentException("Korisnik nije imao održan pregled u centru.");
        }

        boolean hasRatedCenter = rateCenterRepository.existsByUserAndMedicalCenter(userRepository.findById(userId).get(), medicalCenterRepository.findById(medicalCenterId).get());

        if (hasRatedCenter) {
            throw new IllegalArgumentException("Korisnik je već ocenio ovaj centar.");
        }

        RateCenter rateCenter = new RateCenter();
        rateCenter.setUser(userRepository.findById(userId).get());
        rateCenter.setMedicalCenter(medicalCenterRepository.findById(medicalCenterId).get());
        rateCenter.setRating(rating);

        rateCenterRepository.save(rateCenter);
    }

    @Override
    public boolean hasUserVisitedCenter(User user, MedicalCenter medicalCenter) {
        List<Term> userTerms = termRepository.findByUserAndMedicalCenter(user, medicalCenter);

        for (Term term : userTerms) {
            if (term.getStatusOfTerm() == StatusOfTerm.Taken || term.getStatusOfTerm() == StatusOfTerm.Done) {
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
    public void updateRating(Integer userId, Integer medicalCenterId, int newRating) {
        if (newRating < 0 || newRating > 5) {
            throw new IllegalArgumentException("Ocena mora biti između 0 i 5.");
        }
        RateCenter rateCenter = rateCenterRepository.findByUserAndMedicalCenter(userRepository.findById(userId).get(), medicalCenterRepository.findById(medicalCenterId).get());

        if (rateCenter == null) {
            throw new IllegalArgumentException("Korisnik nije ocenio ovaj centar.");
        }
        rateCenter.setRating(newRating);

        rateCenterRepository.save(rateCenter);
    }





}
