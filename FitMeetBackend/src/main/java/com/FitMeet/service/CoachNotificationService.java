package com.FitMeet.service;

import com.FitMeet.dto.CoacheDto;
import com.FitMeet.model.CoachNotification;
import com.FitMeet.model.Coache;

import java.util.List;

public interface CoachNotificationService {
    List<CoachNotification> findAll();

    CoachNotification findById(Long id);

    void deteleById(Long id);

    CoachNotification save(CoachNotification attachment);

    CoachNotification update(CoachNotification attachment);
}
