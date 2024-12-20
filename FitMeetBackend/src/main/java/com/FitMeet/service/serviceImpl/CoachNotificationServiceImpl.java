package com.FitMeet.service.serviceImpl;

import com.FitMeet.model.CoachNotification;
import com.FitMeet.repository.CoachNotificationRepository;
import com.FitMeet.service.CoachNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CoachNotificationServiceImpl implements CoachNotificationService {
    @Autowired
    private CoachNotificationRepository coachNotificationRepository;
    @Override
    public List<CoachNotification> findAll() {
        return coachNotificationRepository.findAll();
    }

    @Override
    public CoachNotification findById(Long id) {
        return null;
    }

    @Override
    public void deteleById(Long id) {

    }

    @Override
    public CoachNotification save(CoachNotification attachment) {
        return coachNotificationRepository.save(attachment);
    }

    @Override
    public CoachNotification update(CoachNotification attachment) {
        return null;
    }
}
