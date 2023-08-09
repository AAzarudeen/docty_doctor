package com.example.doctydoctor.adapters;

import android.net.Uri;

public class PatientDetails {

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    private  String uId;
    private String name;
    private String phone;
    private String gender;
    private String bloodType;
    private String dob;
    private String height;
    private String weight;
    private String doctorID;
    private Uri profile;


    public PatientDetails(String uId, String name, String phone, String gender, String bloodType, String dob, String height, String weight, String doctorID, Uri profile) {
        this.uId = uId;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.bloodType = bloodType;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.doctorID = doctorID;
        this.profile = profile;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public Uri getProfile() {
        return profile;
    }

    public void setProfile(Uri profile) {
        this.profile = profile;
    }
}
