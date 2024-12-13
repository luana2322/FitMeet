package com.FitMeet.service;

import com.FitMeet.model.Chat;
import com.FitMeet.model.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> findAll();

    Schedule findById(Long id);

    void deteleById(Long id);

    Schedule save(Schedule attachment);

    Schedule update(Schedule attachment);
}
