package com.FitMeet.service;

import com.FitMeet.model.CoachNotification;
import com.FitMeet.model.StudentNotification;

import java.util.List;

public interface StudentNotificationService {
    List<StudentNotification> findAll();

    StudentNotification findById(Long id);

    void deteleById(Long id);

    StudentNotification save(StudentNotification attachment);

    StudentNotification update(StudentNotification attachment);
}
