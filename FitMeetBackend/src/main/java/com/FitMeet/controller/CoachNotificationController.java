package com.FitMeet.controller;

import com.FitMeet.model.CoachNotification;
import com.FitMeet.model.Coache;
import com.FitMeet.repository.CoachNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CoachNotificationController {
    @Autowired
    private CoachNotificationRepository coachNotificationRepository;

    @GetMapping("/getlistnotificationcoach")
    public ResponseEntity<List<CoachNotification>> getlistnotificationcoach(@RequestParam("coach_id")  Long student_id) {
        return ResponseEntity.ok(coachNotificationRepository.findListNotificationByCoachId(student_id));
    }


}
