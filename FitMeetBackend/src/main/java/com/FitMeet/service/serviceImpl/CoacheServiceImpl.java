package com.FitMeet.service.serviceImpl;

import com.FitMeet.dto.CoacheDto;
import com.FitMeet.exception.ResourceNotFoundException;
import com.FitMeet.model.Coache;
import com.FitMeet.repository.CoacheRepository;
import com.FitMeet.service.CoacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoacheServiceImpl implements CoacheService {
    @Autowired
    private CoacheRepository coacheRepository;
    @Override
    public List<Coache> findAll() {
        return coacheRepository.findAll();
    }

    @Override
    public Coache findById(Long id) {
        return coacheRepository.findById(id).get();
    }

    @Override
    public void deteleById(Long id) {

    }

    @Override
    public Coache save(Coache attachment) {
        return coacheRepository.save(attachment);
    }

    @Override
    public Coache update(Coache attachment) {
        return null;
    }

    @Override
    public Coache logincoach(CoacheDto coachelogin) {
        List<Coache> listcoache=new ArrayList<>();
        listcoache=coacheRepository.findAll();
        for(Coache coach:listcoache){
                if (coachelogin.getCoachName().equals(coach.getFullname())&&
                coachelogin.getPassworddto().equals(coach.getPassword())){
                    return coach;
                }
        }
        throw new ResourceNotFoundException("Coach with emai "+coachelogin.getEmailcoach()+"not found!!!");
    }
}
