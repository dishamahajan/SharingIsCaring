package com.example.md66805.sharingiscaring.domain;

import java.io.Serializable;

public class ItemDetails implements Serializable{
    private String extn;
    private String name;
    private String domain;
    private String type;

    public ItemDetails(String extn, String name, String domain, String type) {
        this.extn = extn;
        this.name = name;
        this.domain = domain;
        this.type = type;
    }

    public String getExtn() {
        return extn;
    }

    public void setExtn(String extn) {
        this.extn = extn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
