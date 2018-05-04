package com.fyp.ibtehaj.firestore;

/**
 * Created by ibtehaj on 5/4/2018.
 */

public class Medicine {
    String medicineName,
    companyName;
    int price;

    public Medicine() {
    }

    public Medicine(String medicineName, String companyName, int price) {
        this.medicineName = medicineName;
        this.companyName = companyName;
        this.price = price;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
