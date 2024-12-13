package com.FitMeet.service.serviceImpl;

import com.FitMeet.model.Schedule;
import com.FitMeet.repository.ScheduleRepository;
import com.FitMeet.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule findById(Long id) {
        return null;
    }

    @Override
    public void deteleById(Long id) {

    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule update(Schedule attachment) {
        return null;
    }
}
