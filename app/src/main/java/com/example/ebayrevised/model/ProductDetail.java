package com.example.ebayrevised.model;

public class ProductDetail {
    String pid;
    String name;
    String prize;
    String discription;
    String imageurl;
    String quantity;

    public ProductDetail(String pid, String name, String prize, String discription, String imageurl, String quantity) {
        this.pid = pid;
        this.name = name;
        this.prize = prize;
        this.discription = discription;
        this.imageurl = imageurl;
        this.quantity = quantity;
    }

    public ProductDetail() {

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
