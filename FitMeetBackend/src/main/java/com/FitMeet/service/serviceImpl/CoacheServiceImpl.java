package com.FitMeet.service.serviceImpl;

import com.FitMeet.dto.CoachSigupDto;
import com.FitMeet.dto.CoacheDto;
import com.FitMeet.exception.ResourceNotFoundException;
import com.FitMeet.model.Coache;
import com.FitMeet.model.User;
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
    @Autowired
    private UserServiceImpl userServiceImpl;
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

    public Coache signUp(CoachSigupDto coachesignin) {
        List<Coache> listcoache=new ArrayList<>();
        listcoache=coacheRepository.findAll();
        for(Coache coach:listcoache){
            if (coachesignin.getCoachName().equals(coach.getFullname())||
                    coachesignin.getEmailcoach().equals(coach.getEmail())){
                throw new ResourceNotFoundException("Coach with emai "+coachesignin.getEmailcoach()+"not found!!!");
            }else if(coachesignin.getPassworddto().equals(coachesignin.getPassworddtoRepeat())) {
                throw new ResourceNotFoundException("Password does not same!!!!");
            }


        }

        Coache coache=new Coache();
        coache.setEmail(coachesignin.getEmailcoach());
        coache.setFullname(coachesignin.getCoachName());
        coache.setPassword(coachesignin.getPassworddto());

        User user=new User();
        user.setStatus(0);
        user.setUserName(coachesignin.getCoachName());
        user.setGender("Male");
        user.setImageURL("null");
        userServiceImpl.save(user);

        return coacheRepository.save(coache);

    }


}
