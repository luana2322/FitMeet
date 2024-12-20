package com.FitMeet.repository;

import com.FitMeet.model.CoachNotification;
import com.FitMeet.model.Coache;
import com.FitMeet.model.StudentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentNotificationRepository extends JpaRepository<StudentNotification, Long> {
    @Query(value="select n.student_notification_id,n.context,n.created_at,n.title,n.student_id\n" +
            " from student c\n" +
            "join studentnotification n\n" +
            "on c.student_id=n.student_id\n" +
            "where c.student_id=?1\n",nativeQuery = true)
    List<StudentNotification> findListNotificationByStudentId(Long student_id);
}
