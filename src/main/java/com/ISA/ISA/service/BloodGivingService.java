package com.ISA.ISA.service;

import com.ISA.ISA.domain.BloodGiving;
import com.ISA.ISA.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BloodGivingService {
    Page<BloodGiving> GetAllForUser(int userId, Pageable pageable);

    boolean CanUserGiveBlood(int userId);
}
