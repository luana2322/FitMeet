package com.ravisaharan.videocall.dto;

public class CoachDto {
String emailcoach;
    String passworddto;
    String coachName;

    public String getEmailcoach() {
        return emailcoach;
    }

    public CoachDto() {
    }

    public void setEmailcoach(String emailcoach) {
        this.emailcoach = emailcoach;
    }

    public String getPassworddto() {
        return passworddto;
    }

    public void setPassworddto(String passworddto) {
        this.passworddto = passworddto;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }
}
