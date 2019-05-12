package com.example.ebayrevised.model;

public class ProductDetail {

    String name;
    String prize;
    String imageurl;
    String discription;

    public ProductDetail(String name, String prize, String imageurl, String discription) {
        this.name = name;
        this.prize = prize;
        this.imageurl = imageurl;
        this.discription = discription;
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

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
