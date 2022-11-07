package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.MedicalCenterDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MedicalCenterImpl implements MedicalCenterService{
    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

    @Autowired
    private TermRepository termRepository;

    @Override
    public MedicalCenter add(MedicalCenterDTO medicalCenterDTO){
        MedicalCenter medicalCenter = new MedicalCenter();

        medicalCenter.setCenterName(medicalCenterDTO.getCenterName());
        medicalCenter.setAdress(medicalCenterDTO.getAddress());
        medicalCenter.setDescription(medicalCenterDTO.getDescription());
        medicalCenter.setAverageRating(medicalCenterDTO.getAverageRating());
        medicalCenter.setTerm(termRepository.getReferenceById(medicalCenterDTO.getTermID()));

        return medicalCenter;
    }

    @Override
    public MedicalCenter edit(MedicalCenterDTO medicalCenterDTO){
        MedicalCenter medicalCenter = medicalCenterRepository.getReferenceById(medicalCenterDTO.getTermID());
        medicalCenter = MedicalCenterDTO.convertBack(medicalCenterDTO);
        //??????????????????????????????????????????????????
        medicalCenterRepository.save(medicalCenter);
        return medicalCenter;
    }

    @Override
    public void delete(int id){
        medicalCenterRepository.deleteById(id);
    }

    @Override
    public MedicalCenter findById(int id){
        return medicalCenterRepository.getReferenceById(id);
    }

    @Override
    public List<MedicalCenter> getAll(){
        return medicalCenterRepository.findAll();
    }
}
