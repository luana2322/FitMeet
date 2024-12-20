package com.FitMeet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name="student_id")
    private Long student_id;
    private String email;
    private String password;
    private String fullname;
    private String picture;
    private String gender;
    private String dateofbirth;
    private String phonenumber;
    private String address ;
    @OneToMany(
            mappedBy = "student"
    )
    @JsonManagedReference
    private List<Schedule> scheduleList;
    @OneToMany(
            mappedBy = "student"
    )
    @JsonManagedReference
    private List<Review> reviewList;
    @OneToMany(
            mappedBy = "student"
    )
    @JsonManagedReference
    private List<StudentNotification> studentNotificationList;
}
