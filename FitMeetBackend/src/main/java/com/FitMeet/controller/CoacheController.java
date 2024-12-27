package com.FitMeet.controller;

import com.FitMeet.dto.CoachSigupDto;
import com.FitMeet.dto.CoacheDto;
import com.FitMeet.dto.StudentDto;
import com.FitMeet.exception.NotFoundException;
import com.FitMeet.model.Coache;
import com.FitMeet.model.Student;
import com.FitMeet.repository.CoacheRepository;
import com.FitMeet.repository.ScheduleRepository;
import com.FitMeet.service.serviceImpl.CoacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CoacheController {
    @Autowired
    private CoacheServiceImpl coacheServiceImpl;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private CoacheRepository coacheRepository;
    @GetMapping("/getallcoach")
    public ResponseEntity<List<Coache>> getcoach() {
        return ResponseEntity.ok(coacheServiceImpl.findAll());
    }

            @GetMapping("/getcoachbyId")
    public ResponseEntity<Coache> getCoachebyuserId(@RequestParam("coachId") Long coachId) {
        Coache coach= coacheServiceImpl.findById(coachId);
        return ResponseEntity.ok(coach);
    }


    @PostMapping("/coachlogin")
    @ResponseBody
    public ResponseEntity<Coache> login(@RequestBody CoacheDto userDto) throws NotFoundException {
       Coache coache=coacheServiceImpl.logincoach(userDto);

        return ResponseEntity.ok(coache);
    }
    @GetMapping("/getlistcoachchat")
    public ResponseEntity<List<Coache>> getListCoachChat(@RequestParam("student_id")  Long student_id) {
        System.out.println(student_id);
        return ResponseEntity.ok(coacheRepository.findListCoachByStudentId(student_id));
    }

    @PostMapping("/coachsignup")
    @ResponseBody
    public ResponseEntity<Coache> signup(@RequestBody CoachSigupDto userDto) throws NotFoundException {
        Coache coache=coacheServiceImpl.signUp(userDto);

        return ResponseEntity.ok(coache);
    }


}
