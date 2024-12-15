package com.ravisaharan.videocall.dto;

public class StudentDto {
    String emailstudent;
    String passworddto;
    String studentName;

    public String getEmailstudent() {
        return emailstudent;
    }

    public void setEmailstudent(String emailstudent) {
        this.emailstudent = emailstudent;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public StudentDto() {
    }

    public String getPassworddto() {
        return passworddto;
    }

    public void setPassworddto(String passworddto) {
        this.passworddto = passworddto;
    }
}
