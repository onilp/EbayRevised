package com.example.ebayrevised.model;

public class SubCategory {
    String scid, scname,scdiscription,scimagerl;

    public SubCategory(String scid, String scname, String scdiscription, String scimagerl) {
        this.scid = scid;
        this.scname = scname;
        this.scdiscription = scdiscription;
        this.scimagerl = scimagerl;
    }

    public String getScid() {
        return scid;
    }

    public void setScid(String scid) {
        this.scid = scid;
    }

    public String getScname() {
        return scname;
    }

    public void setScname(String scname) {
        this.scname = scname;
    }

    public String getScdiscription() {
        return scdiscription;
    }

    public void setScdiscription(String scdiscription) {
        this.scdiscription = scdiscription;
    }

    public String getScimagerl() {
        return scimagerl;
    }

    public void setScimagerl(String scimagerl) {
        this.scimagerl = scimagerl;
    }
}
