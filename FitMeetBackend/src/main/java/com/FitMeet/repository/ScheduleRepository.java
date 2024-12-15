package com.FitMeet.repository;

import com.FitMeet.model.Coache;
import com.FitMeet.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository  extends JpaRepository<Schedule, Long> {


}
