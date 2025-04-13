package com.example.hotelbookingapp.data.models;

public class SimpleHotel {
    private String name;
    private String imageUrl;

    public SimpleHotel() {}

    public SimpleHotel(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return name; // để Spinner hiển thị tên khách sạn
    }

}
