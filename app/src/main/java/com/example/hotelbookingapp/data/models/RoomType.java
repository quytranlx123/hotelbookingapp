package com.example.hotelbookingapp.data.models;

import java.util.List;

public class RoomType {
    private String roomType;
    private List<Room> rooms;

    public RoomType(List<Room> rooms, String roomCategory) {
        this.rooms = rooms;
        this.roomType = roomCategory;
    }

    public String getRoomCategory() {
        return roomType;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomType = roomCategory;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
