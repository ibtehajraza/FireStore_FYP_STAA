package com.fyp.ibtehaj.firestore;

/**
 * Created by ibtehaj on 2/11/2018.
 */

public class Schedule {

    private String brick;
    private String day;
    private String docId;
    private String gps;
    private String timeStamp;
    private String tom;
    private String docArea;
    private String docContact;
    private String docName;
    private String docSpecialization;



    public Schedule() {
    }

    public Schedule(String brick, String day, String docId, String gps,
                    String timeStamp, String tom, String docArea, String docContact,
                    String docName, String docSpecialization) {
        this.brick = brick;
        this.day = day;
        this.docId = docId;
        this.gps = gps;
        this.timeStamp = timeStamp;
        this.tom = tom;
        this.docArea = docArea;
        this.docContact = docContact;
        this.docName = docName;
        this.docSpecialization = docSpecialization;
    }

    public String getDocArea() {
        return docArea;
    }

    public void setDocArea(String docArea) {
        this.docArea = docArea;
    }

    public String getDocContact() {
        return docContact;
    }

    public void setDocContact(String docContact) {
        this.docContact = docContact;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocSpecialization() {
        return docSpecialization;
    }

    public void setDocSpecialization(String docSpecialization) {
        this.docSpecialization = docSpecialization;
    }

    public String getBrick() {
        return brick;
    }

    public void setBrick(String brick) {
        this.brick = brick;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTom() {
        return tom;
    }

    public void setTom(String tom) {
        this.tom = tom;
    }
}
