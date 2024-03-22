package org.example;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

public class Customer {

    private String id;
    private String firstname;
    private String lastname;
    private String telNr;
    private String licenseNr;

    private String orders;

    public Customer(long counter, String id, String firstname, String lastname, String telNr, String licenseNr, String orders) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.telNr = telNr;
        this.licenseNr = licenseNr;
        this.orders = orders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTelNr() {
        return telNr;
    }

    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

    public String getLicenseNr() {
        return licenseNr;
    }

    public void setLicenseNr(String licenseNr) {
        this.licenseNr = licenseNr;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }
}
