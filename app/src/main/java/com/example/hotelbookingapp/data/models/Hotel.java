package com.example.hotelbookingapp.data.models;

public class Hotel {
    private String id; // Document ID
    private String name;
    private String address;
    private double price;
    private float rating;
    private String imageUrl;

    public Hotel(String vinpearlResort, String url, String s) {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Hotel() {
    } // Cần cho Firebase

    public Hotel(String id, String name, String address, double price, float rating, String imageUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    // Getters và Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public double getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }
}
