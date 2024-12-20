package com.FitMeet.dto;

import com.FitMeet.model.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDto {
     String createdAt;
     int student_id;
     int coache_id;
}
