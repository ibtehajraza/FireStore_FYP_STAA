package com.fyp.ibtehaj.firestore;

/**
 * Created by ibtehaj on 2/6/2018.
 */

public class Meeting {
    private String clientName, timeStamp, gps;

    public Meeting(String clientName, String timeStamp, String gps) {
        this.clientName = clientName;
        this.timeStamp = timeStamp;
        this.gps = gps;
    }

    public Meeting() {
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }
}
