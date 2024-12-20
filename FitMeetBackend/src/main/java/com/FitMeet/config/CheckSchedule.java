package com.FitMeet.config;

import com.FitMeet.model.*;
import com.FitMeet.service.serviceImpl.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckSchedule {
    @Autowired
    private ScheduleServiceImpl scheduleServiceImpl;
    @Autowired
    private ReviewServiceImpl reviewServiceImpl;
    @Autowired
    private StudentNotificationServiceImpl studentNotificationServiceImpl;
    @Autowired
    private CoachNotificationServiceImpl coachNotificationServiceImpl;
    @Autowired
    private StudentServiceImpl studentServiceImpl;
    @Autowired
    private CoacheServiceImpl coacheServiceImpl;

    @PostConstruct
    public void checkschedule() {
        List<Schedule> scheduleList=new ArrayList<>();
        scheduleList=scheduleServiceImpl.findAll();
        for (Schedule schedule:scheduleList){

            // Ngày hôm nay
            LocalDate today = LocalDate.now();
            if (schedule.getTimeStart().isEqual(today)){

                StudentNotification studentNotification=new StudentNotification();
                studentNotification.setTitle("Cuộc hẹn với "+schedule.getCoache().getFullname());
                studentNotification.setStudent(schedule.getStudent());
                studentNotification.setCreated_at(today);
                studentNotification.setContext("Hôm nay có cuộc hẹn rèn luyện với huấn luyện viên. Đừng quên tham gia!");
                studentNotificationServiceImpl.save(studentNotification);

                CoachNotification coachNotification=new CoachNotification();
                coachNotification.setTitle("Cuộc hẹn với "+schedule.getCoache().getFullname());
                coachNotification.setCoache(schedule.getCoache());
                coachNotification.setCreated_at(today);
                coachNotification.setContext("Hôm nay có cuộc hẹn rèn luyện với huấn luyện viên. Đừng quên tham gia!");
                coachNotificationServiceImpl.save(coachNotification);
            }
        }
    }
}
