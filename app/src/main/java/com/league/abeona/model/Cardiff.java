package com.league.abeona.model;

/**
 * Created by Jimmy on 1/2/2017.
 */

public class Cardiff {
    private int ID;
    private String Title;
    private String Content;
    private String Image;
    private double Latitude;
    private double Longitude;
    private String Address;
    private String Phone;
    private String Hours;
    private String Type;
    private String TargetMarket;

    public Cardiff(int ID, String title, String content, String image, double latitude, double longitude, String address, String phone, String hours, String type, String targetMarket) {
        this.ID = ID;
        Title = title;
        Content = content;
        Image = image;
        Latitude = latitude;
        Longitude = longitude;
        Address = address;
        Phone = phone;
        Hours = hours;
        Type = type;
        TargetMarket = targetMarket;
    }

    public Cardiff() {
        this.ID = 0;
        Title = "";
        Content = "";
        Image = "";
        Latitude = 0.0f;
        Longitude = 0.0f;
        Address = "";
        Phone = "";
        Hours = "";
        Type = "";
        TargetMarket = "";
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getHours() {
        return Hours;
    }

    public void setHours(String hours) {
        Hours = hours;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTargetMarket() {
        return TargetMarket;
    }

    public void setTargetMarket(String targetMarket) {
        TargetMarket = targetMarket;
    }
}
