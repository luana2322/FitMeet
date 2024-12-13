package com.FitMeet.controller;

import com.FitMeet.dto.StudentDto;
import com.FitMeet.exception.NotFoundException;
import com.FitMeet.model.Student;
import com.FitMeet.service.serviceImpl.StudentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StudentController {
@Autowired
private StudentServiceImpl studentServiceImpl;



    @GetMapping("/getallstudent")
    public ResponseEntity<List<Student>> searchproject() {
        return ResponseEntity.ok(studentServiceImpl.findAll());
    }
//    @PostMapping("/registerstudent")
//    public ResponseEntity<Student> register(@RequestBody @Valid StudentDto userDto
//            , BindingResult bindingResult ) throws Exception {
////if (bindingResult.hasErrors()){
////    throw new  Exception("Wrong data");
////}
//        return ResponseEntity.ok(userServiceImpl.save(userDto));
//    }

    @PostMapping("/studentlogin")
    @ResponseBody
    public ResponseEntity<Student> login(@RequestBody StudentDto userDto) throws NotFoundException {
        Student student=studentServiceImpl.loginstudent(userDto);
        return ResponseEntity.ok(student);
    }
}
