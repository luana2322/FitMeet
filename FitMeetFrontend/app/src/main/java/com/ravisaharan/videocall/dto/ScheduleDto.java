package com.ravisaharan.videocall.dto;

public class ScheduleDto {
    int student_id;
    int coache_id;

    public ScheduleDto(int student_id, int coache_id, String createdAt) {
        this.student_id = student_id;
        this.coache_id = coache_id;
        this.createdAt = createdAt;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getCoache_id() {
        return coache_id;
    }

    public void setCoache_id(int coache_id) {
        this.coache_id = coache_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ScheduleDto() {
    }

    String createdAt;
}
