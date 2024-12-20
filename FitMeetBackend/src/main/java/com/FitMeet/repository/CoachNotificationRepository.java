package com.FitMeet.repository;

import com.FitMeet.model.CoachNotification;
import com.FitMeet.model.Coache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachNotificationRepository extends JpaRepository<CoachNotification, Long> {
    @Query(value="select n.coach_notification_id,n.context,n.created_at,n.title,n.coach_id\n" +
            " from coache c\n" +
            "join coachnotification n\n" +
            "on c.coach_id=n.coach_id\n" +
            "where c.coach_id=?1\n",nativeQuery = true)
    List<CoachNotification> findListNotificationByCoachId(Long coach_id);
}
