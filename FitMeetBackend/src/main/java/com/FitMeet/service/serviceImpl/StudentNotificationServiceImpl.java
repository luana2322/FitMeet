package com.FitMeet.service.serviceImpl;

import com.FitMeet.model.StudentNotification;
import com.FitMeet.repository.StudentNotificationRepository;
import com.FitMeet.service.StudentNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentNotificationServiceImpl implements StudentNotificationService {
    @Autowired
    private StudentNotificationRepository studentNotificationRepository;
    @Override
    public List<StudentNotification> findAll() {
        return studentNotificationRepository.findAll();
    }

    @Override
    public StudentNotification findById(Long id) {
        return null;
    }

    @Override
    public void deteleById(Long id) {

    }

    @Override
    public StudentNotification save(StudentNotification attachment) {
        return studentNotificationRepository.save(attachment);
    }

    @Override
    public StudentNotification update(StudentNotification attachment) {
        return null;
    }
}
