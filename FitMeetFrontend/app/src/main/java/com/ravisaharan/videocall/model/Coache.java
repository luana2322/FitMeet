package com.ravisaharan.videocall.model;

import com.google.gson.annotations.SerializedName;

public class Coache {
    @SerializedName("coach_id")
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
    // Getter và Setter cho coach_id

    public Long getCoachId() {
        return coach_id;
    }

    public void setCoachId(Long coach_id) {
        this.coach_id = coach_id;
    }

    // Getter và Setter cho email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter và Setter cho password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter và Setter cho fullname
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    // Getter và Setter cho picture
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    // Getter và Setter cho gender
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getter và Setter cho dateofbirth
    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    // Getter và Setter cho phonenumber
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    // Getter và Setter cho address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter và Setter cho specialties
    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    // Getter và Setter cho description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter và Setter cho priceperhour
    public Double getPriceperhour() {
        return priceperhour;
    }

    public void setPriceperhour(Double priceperhour) {
        this.priceperhour = priceperhour;
    }
}
