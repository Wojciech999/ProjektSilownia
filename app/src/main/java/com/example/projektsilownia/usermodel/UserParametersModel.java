package com.example.projektsilownia.usermodel;

public class UserParametersModel {

    public String old, weight, height, bmi;

    public UserParametersModel(String old, String weight, String height, String bmi) {
        this.old = old;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }
}
