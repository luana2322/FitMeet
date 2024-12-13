package com.FitMeet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name="chat_id")
    private Long chat_id;

}
