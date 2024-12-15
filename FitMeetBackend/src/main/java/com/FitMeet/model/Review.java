package com.FitMeet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name="review_id")
    private Long review_id;
    private int rating;
    private String comment;
    private LocalDate createdAt;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "student_id"
    )
    @JsonIgnore
    private Student student;

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
