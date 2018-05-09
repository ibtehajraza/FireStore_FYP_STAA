package com.fyp.ibtehaj.firestore;

/**
 * Created by ibtehaj on 5/4/2018.
 */

public class Medicine {
    String medicineName,
    companyName;
    String price;

    public Medicine() {
    }

    public Medicine(String medicineName, String companyName, String price) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
