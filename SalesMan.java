package com.fyp.ibtehaj.firestore;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Created by ibtehaj on 2/1/2018.
 */

public class SalesMan {
    private String name;
    private String email;
    private String phoneNo;
    private String area;
    private String score;

    public SalesMan(String name, String email, String phoneNo, String area, String score) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.area = area;
        this.score = score;
    }




    public SalesMan(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
