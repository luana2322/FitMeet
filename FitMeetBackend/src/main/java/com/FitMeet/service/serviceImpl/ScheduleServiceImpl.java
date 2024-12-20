package com.FitMeet.service.serviceImpl;

import com.FitMeet.dto.ScheduleDto;
import com.FitMeet.model.Schedule;
import com.FitMeet.model.Status;
import com.FitMeet.repository.ScheduleRepository;
import com.FitMeet.repository.StudentRepository;
import com.FitMeet.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private StudentServiceImpl studentServiceImpl;
    @Autowired
    private CoacheServiceImpl coacheServiceImpl;
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
    public Schedule save(ScheduleDto scheduledto) {
        Schedule schedule=new Schedule();
        schedule.setCoache(coacheServiceImpl.findById((long)scheduledto.getCoache_id()));
        schedule.setStudent(studentServiceImpl.findById((long)scheduledto.getStudent_id()));
        schedule.setStatus(Status.PENDING);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


        // Chuyển đổi từ String sang LocalDate
        LocalDate localDate = LocalDate.parse(scheduledto.getCreatedAt(), formatter);
        schedule.setTimeStart(localDate);
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule update(Schedule attachment) {
        return null;
    }
}
