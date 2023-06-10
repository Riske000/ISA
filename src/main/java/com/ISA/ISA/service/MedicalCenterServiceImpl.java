package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.MedicalCenterDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.RateCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.repository.RateCenterRepository;
import com.ISA.ISA.repository.TermRepository;
import com.ISA.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
@Service
public class MedicalCenterServiceImpl implements MedicalCenterService{
    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RateCenterRepository rateCenterRepository;

    @Override
    public MedicalCenter add(MedicalCenterDTO medicalCenterDTO){
        MedicalCenter medicalCenter = new MedicalCenter();

        medicalCenter = MedicalCenterDTO.convertBack(medicalCenterDTO);
        medicalCenter = medicalCenterRepository.save(medicalCenter);
        return medicalCenter;
    }

    @Override
    public MedicalCenter edit(MedicalCenterDTO medicalCenterDTO){
        Optional<MedicalCenter> medicalCenter = medicalCenterRepository.findById(medicalCenterDTO.getId());
        if (medicalCenter.isEmpty()){
            return null;
        }

        medicalCenter.get().setCenterName(medicalCenterDTO.getCenterName());
        medicalCenter.get().setAdress(medicalCenterDTO.getAddress());
        medicalCenter.get().setDescription(medicalCenterDTO.getDescription());
        medicalCenter.get().setAverageRating(medicalCenterDTO.getAverageRating());

        return medicalCenterRepository.save(medicalCenter.get());
    }

    @Override
    public void delete(int id){
        Optional<MedicalCenter> medicalCenter = medicalCenterRepository.findById(id);
        if (medicalCenter.isPresent()){
        medicalCenter.get().setDeleted(true);
        }
    }

    @Override
    public Optional<MedicalCenter> findById(int id){
        return medicalCenterRepository.findById(id);
    }

    @Override
    public List<MedicalCenter> getAll(){
        return medicalCenterRepository.findAll();
    }

    @Override
    public Page<MedicalCenter> getSorted(String field, int pageNo, int pageSize, String sortMode){
        Pageable paging;
        String s = "";

        if(sortMode.equals("Ascending")) {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, field));
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, field));
        }

        return medicalCenterRepository.findAll(paging);
    }

    public int getNumberOfCenters(){
        return getAll().size();
    }

    @Override
    public List<Term> getTermsByMedicalCenterId(Integer medicalCenterId) {
        return termRepository.findByMedicalCenterId(medicalCenterId);
    }


    @Override
    public MedicalCenterDTO updateWorkingHours(int id, LocalTime startTime, LocalTime endTime) {
        MedicalCenter medicalCenter = medicalCenterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical Center not found"));

        if (startTime != null) {
            medicalCenter.setStartTime(startTime);
        }

        if (endTime != null) {
            medicalCenter.setEndTime(endTime);
        }

        MedicalCenter updatedMedicalCenter = medicalCenterRepository.save(medicalCenter);

        MedicalCenterDTO updatedMedicalCenterDTO = MedicalCenterDTO.convertToDTO(updatedMedicalCenter);
        updatedMedicalCenterDTO.setStartTime(updatedMedicalCenter.getStartTime());
        updatedMedicalCenterDTO.setEndTime(updatedMedicalCenter.getEndTime());

        return updatedMedicalCenterDTO;
    }

    @Override
    public List<MedicalCenter> searchMedicalCentersByCenterNameAndAdressIgnoreCase(String centerName, String adress) {
        return medicalCenterRepository.findByCenterNameContainingIgnoreCaseAndAdressContainingIgnoreCase(centerName, adress);
    }

    @Override
    public List<MedicalCenter> searchMedicalCentersByCenterNameIgnoreCase(String centerName) {
        return medicalCenterRepository.findByCenterNameContainingIgnoreCase(centerName);
    }

    @Override
    public List<MedicalCenter> searchMedicalCentersByAdressIgnoreCase(String adress) {
        return medicalCenterRepository.findByAdressContainingIgnoreCase(adress);
    }

    @Override
    public List<MedicalCenter> filterMedicalCentersByRating(Double minRating) {
        return medicalCenterRepository.findByAverageRatingGreaterThanEqual(minRating);
    }

    @Override
    public MedicalCenterDTO updateCenterInfo(int id, String centerName, String address, String description, Double averageRating) {
        MedicalCenter medicalCenter = medicalCenterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical Center not found"));

        if (centerName != null) {
            medicalCenter.setCenterName(centerName);
        }

        if (address != null) {
            medicalCenter.setAdress(address);
        }

        if (description != null) {
            medicalCenter.setDescription(description);
        }

        if (averageRating != null) {
            medicalCenter.setAverageRating(averageRating);
        }

        MedicalCenter updatedMedicalCenter = medicalCenterRepository.save(medicalCenter);

        MedicalCenterDTO updatedMedicalCenterDTO = MedicalCenterDTO.convertToDTO(updatedMedicalCenter);
        updatedMedicalCenterDTO.setStartTime(updatedMedicalCenter.getStartTime());
        updatedMedicalCenterDTO.setEndTime(updatedMedicalCenter.getEndTime());

        return updatedMedicalCenterDTO;
    }

    @Override
    public double averageRating(Integer medicalCenterId){
        MedicalCenter medicalCenter = medicalCenterRepository.findById(medicalCenterId).orElse(null);
        if (medicalCenter == null) {
            return 0.0;
        }

        List<RateCenter> rateCenters = rateCenterRepository.findByMedicalCenter(medicalCenter);
        if (rateCenters.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        for (RateCenter rateCenter : rateCenters) {
            sum += rateCenter.getRating();
        }

        double averageGrade = (double) sum / rateCenters.size();
        return averageGrade;


    }
}
