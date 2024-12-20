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
@Table(name="coachnotification")
public class CoachNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name="coach_notification_id")
    private Long coach_notification_id;
    private String title;
    private String context;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate created_at;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "coach_id",
            referencedColumnName = "coach_id"
    )
    @JsonIgnore
    private Coache coache;
}
