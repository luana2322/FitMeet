package com.FitMeet.service;

import com.FitMeet.dto.StudentDto;
import com.FitMeet.model.Chat;
import com.FitMeet.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findById(Long id);

    void deteleById(Long id);

    Student save(Student attachment);

    Student update(Student attachment);

    Student loginstudent(StudentDto student);
}
