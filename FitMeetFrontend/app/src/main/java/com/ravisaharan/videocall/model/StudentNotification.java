package com.ravisaharan.videocall.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class StudentNotification {
    private Long student_notification_id;
    private String title;
    private String context;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String created_at;

    public StudentNotification(Long student_notification_id, String title, String context, String created_at) {
        this.student_notification_id = student_notification_id;
        this.title = title;
        this.context = context;
        this.created_at = created_at;
    }

    public StudentNotification() {
    }

    public Long getStudent_notification_id() {
        return student_notification_id;
    }

    public void setStudent_notification_id(Long student_notification_id) {
        this.student_notification_id = student_notification_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


}
