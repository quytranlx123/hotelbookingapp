package com.example.hotelbookingapp.data.models;

public class Booking {
    private String id;
    private String hotelName;
    private String roomType;
    private String checkInDate;
    private String checkOutDate;
    private double totalPrice;

    public Booking(String id, String hotelName, String roomType, String checkInDate, String checkOutDate, double totalPrice) {
        this.id = id;
        this.hotelName = hotelName;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
    }

    // Getter Setter


    public void setId(String id) {
        this.id = id;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
