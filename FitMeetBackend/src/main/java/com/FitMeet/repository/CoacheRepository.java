package com.FitMeet.repository;

import com.FitMeet.model.Coache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoacheRepository  extends JpaRepository<Coache, Long> {
    @Query(value="select c.coach_id,c.address,c.dateofbirth,c.description,c.email,c.fullname,c.gender,c.password,c.phonenumber,c.picture,c.priceperhour,c.specialties,s.student_id\n" +
            " from coache c\n" +
            "join schedule s\n" +
            "on c.coach_id=s.coach_id\n" +
            "where s.student_id=?1\n",nativeQuery = true)
    List<Coache> findListCoachByStudentId(Long userId);


}
