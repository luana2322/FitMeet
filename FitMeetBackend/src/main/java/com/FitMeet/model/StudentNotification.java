package com.FitMeet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="studentnotification")
public class StudentNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name="student_notification_id")
    private Long student_notification_id;
    private String title;
    private String context;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate created_at;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "student_id"
    )
    @JsonIgnore
    private Student student;
}
