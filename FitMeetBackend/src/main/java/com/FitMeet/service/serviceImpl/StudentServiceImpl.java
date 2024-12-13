package com.FitMeet.service.serviceImpl;

import com.FitMeet.dto.StudentDto;
import com.FitMeet.exception.NotFoundException;
import com.FitMeet.exception.ResourceNotFoundException;
import com.FitMeet.model.Coache;
import com.FitMeet.model.Student;
import com.FitMeet.repository.StudentRepository;
import com.FitMeet.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public void deteleById(Long id) {

    }

    @Override
    public Student save(Student attachment) {
        return studentRepository.save(attachment);
    }

    @Override
    public Student update(Student attachment) {
        return null;
    }

    @Override
    public Student loginstudent( StudentDto studentlogin)  {
        List<Student> liststudent=new ArrayList<>();
        liststudent=studentRepository.findAll();
        for(Student coach:liststudent){
            if (studentlogin.getStudentName().equals(coach.getFullname())&&
                    studentlogin.getPassworddto().equals(coach.getPassword())){
                return coach;
            }
        }
        throw new ResourceNotFoundException("Student with emai "+studentlogin.getEmailstudent()+"not found!!!");
    }
}
