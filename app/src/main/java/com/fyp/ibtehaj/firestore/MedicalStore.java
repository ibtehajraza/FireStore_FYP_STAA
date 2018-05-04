package com.fyp.ibtehaj.firestore;

/**
 * Created by ibtehaj on 5/3/2018.
 */

public class MedicalStore {
    private String address,
    contact,
    name;

    public MedicalStore(String address, String contact, String name) {
        this.address = address;
        this.contact = contact;
        this.name = name;
    }

    public MedicalStore() {

    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setName(String name) {
        this.name = name;
    }
}
