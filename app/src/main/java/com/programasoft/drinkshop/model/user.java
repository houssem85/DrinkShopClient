package com.programasoft.drinkshop.model;

/**
 * Created by ASUS on 16/12/2018.
 */

public class user {
    private String phone;
    private String name;
    private String birthdate;
    private String address;
    private String error;
    private String avat;

    public user(String phone, String name, String birthdate, String address) {
        this.phone = phone;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
    }

    public String getAvat() {
        return avat;
    }

    public void setAvat(String avat) {
        this.avat = avat;
    }

    public user() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
