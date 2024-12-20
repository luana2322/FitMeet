package com.FitMeet.controller;

import com.FitMeet.model.CoachNotification;
import com.FitMeet.model.StudentNotification;
import com.FitMeet.repository.StudentNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentNotificationController {
    @Autowired
    private StudentNotificationRepository studentNotificationRepository;

    @GetMapping("/getlistnotificationstudent")
    public ResponseEntity<List<StudentNotification>> getlistnotificationstudent(@RequestParam("student_id")  Long student_id) {
        return ResponseEntity.ok(studentNotificationRepository.findListNotificationByStudentId(student_id));
    }
}
