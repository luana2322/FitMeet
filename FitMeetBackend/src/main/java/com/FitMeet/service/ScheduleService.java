package com.FitMeet.service;

import com.FitMeet.dto.ScheduleDto;
import com.FitMeet.model.Chat;
import com.FitMeet.model.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> findAll();

    Schedule findById(Long id);

    void deteleById(Long id);

    Schedule save(ScheduleDto attachment);

    Schedule update(Schedule attachment);
}
