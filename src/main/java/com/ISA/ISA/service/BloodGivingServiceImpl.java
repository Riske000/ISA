package com.ISA.ISA.service;

import com.ISA.ISA.domain.BloodGiving;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.repository.BloodGivingRepository;
import com.ISA.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BloodGivingServiceImpl implements BloodGivingService{
    @Autowired
    private BloodGivingRepository bloodGivingRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<BloodGiving> GetAllForUser(int userId, Pageable pageable){

        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return null;
        }

        Page<BloodGiving> bloodGivings = bloodGivingRepository.findAllByUser(user.get(), pageable);
        return bloodGivings;
    }

    public boolean CanUserGiveBlood(int userId){
        Date today = new Date();
        Optional<User> user = userRepository.findById(userId);
        BloodGiving lastGiving = bloodGivingRepository.findFirstByUserOrderByDateDesc(user.get());
        if(lastGiving == null){
            return true;
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        Date sixMonthsAgo = cal.getTime();

        if(lastGiving.getDate().after(sixMonthsAgo)){
            return false;
        }

        return true;
    }

}
