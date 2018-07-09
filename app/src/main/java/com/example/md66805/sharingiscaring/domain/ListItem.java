package com.example.md66805.sharingiscaring.domain;

import java.io.Serializable;
import java.util.List;

public class ListItem implements Serializable {

    private String heading;
    private String count;
    private String racf;
    private String domain;
    private List<ItemDetails> itemDetails;

    public ListItem(String heading, String count, String racf, String domain, List<ItemDetails> itemDetails) {
        this.heading = heading;
        this.count = count;
        this.racf = racf;
        this.domain = domain;
        this.itemDetails = itemDetails;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ItemDetails> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetails> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public String getRacf() {
        return racf;
    }

    public void setRacf(String racf) {
        this.racf = racf;
    }

    public String getDomain() { return domain; }

    public void setDomain(String domain) { this.domain = domain; }
}
