package com.ravisaharan.videocall.dto;



public class CoachSigupDto {
    String emailcoach;

    public CoachSigupDto(String emailcoach, String passworddto, String coachName, String passworddtoRepeat) {
        this.emailcoach = emailcoach;
        this.passworddto = passworddto;
        this.coachName = coachName;
        this.passworddtoRepeat = passworddtoRepeat;
    }

    public String getEmailcoach() {
        return emailcoach;
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

    public String getPassworddtoRepeat() {
        return passworddtoRepeat;
    }

    public void setPassworddtoRepeat(String passworddtoRepeat) {
        this.passworddtoRepeat = passworddtoRepeat;
    }

    public CoachSigupDto() {
    }

    String passworddto;
   String coachName;
  String passworddtoRepeat    ;
}
