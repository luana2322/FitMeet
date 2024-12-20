package com.FitMeet.repository;

import com.FitMeet.model.Coache;
import com.FitMeet.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository  extends JpaRepository<Student, Long> {

    @Query(value="select c.student_id,c.address,c.dateofbirth,c.email,c.fullname,c.gender,c.picture,c.password,c.phonenumber,s.coach_id\n" +
            " from student c\n" +
            "join schedule s\n" +
            "on c.student_id=s.student_id\n" +
            "where s.coach_id=?1",nativeQuery = true)
    List<Student> findListStudentByCoachId(Long userId);


}
