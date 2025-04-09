package com.example.hotelbookingapp.data.models;

public class Room {
    private String id;
    private String roomNumber;
    private String roomType;
    private String price;
    private String status;
    private String imageUrl;

    public Room() {} // Required for Firestore

    public Room(String id, String roomNumber, String roomType, String price, String status, String imageUrl) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
        this.imageUrl = imageUrl;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}