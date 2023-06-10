package com.ISA.ISA.service;

import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.User;

public interface RateCenterService {
    void rateMedicalCenter(Integer userId, Integer medicalCenterId, int rating);

    boolean hasUserVisitedCenter(User user, MedicalCenter medicalCenter);

    void updateRating(Integer userId, Integer medicalCenterId, int newRating);
}
