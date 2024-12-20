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
@Table(name="coache")
public class Coache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name="coach_id")
    private Long coach_id;
    private String email;
    private String password;
    private String fullname;
    private String picture;
    private String gender;
    private String dateofbirth;
    private String phonenumber;
    private String address ;
    private String specialties;
    private String description;
    private Double priceperhour;
    @OneToMany(
            mappedBy = "coache"
    )
    @JsonManagedReference
    private List<Schedule> scheduleList;
    @OneToMany(
            mappedBy = "coache"
    )
    @JsonManagedReference
    private List<Review> reviewList;

    @OneToMany(
            mappedBy = "coache"
    )
    @JsonManagedReference
    private List<CoachNotification> coachNotificationList;
}
