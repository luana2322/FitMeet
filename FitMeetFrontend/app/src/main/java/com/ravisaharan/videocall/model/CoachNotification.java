package com.ravisaharan.videocall.model;

import java.time.LocalDate;

public class CoachNotification {
    private Long coach_notification_id;
    private String title;
    private String context;

    public CoachNotification() {
    }

    public Long getCoach_notification_id() {
        return coach_notification_id;
    }

    public void setCoach_notification_id(Long coach_notification_id) {
        this.coach_notification_id = coach_notification_id;
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

    public CoachNotification(Long coach_notification_id, String title, String context, String created_at) {
        this.coach_notification_id = coach_notification_id;
        this.title = title;
        this.context = context;
        this.created_at = created_at;
    }

    private String created_at;
}
