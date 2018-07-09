package com.example.md66805.sharingiscaring.domain;

import java.io.Serializable;

public class ItemDetails implements Serializable{
    private String serialNumber;
    private String racfId;
    private String owner;
    private String domain;
    private String model;
    private String checkInDate;

    public ItemDetails(String serialNumber, String racfId, String owner, String domain, String model, String checkInDate) {
        this.serialNumber = serialNumber;
        this.racfId = racfId;
        this.owner = owner;
        this.domain = domain;
        this.model = model;
        this.checkInDate = checkInDate;
    }


    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRacfId() {
        return racfId;
    }

    public void setRacfId(String racfId) {
        this.racfId = racfId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
