package com.example.hotelbookingapp.data.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RoomType {

    private String id;
    private String hotelId; // Khóa ngoại đến Hotel
    private String name;
    private String description;
    private double pricePerNight;
    private List<String> imageUrls;
    private List<String> roomIds; // Danh sách các Room Id (khoá ngoại đến Room)

    // Firebase yêu cầu constructor rỗng
    public RoomType() {}

    public RoomType(String id, String hotelId, String name, String description, double pricePerNight, List<String> imageUrls, List<String> roomIds) {
        this.id = id;
        this.hotelId = hotelId;
        this.name = name;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.imageUrls = imageUrls;
        this.roomIds = roomIds;
    }

    // Getter
    @NonNull
    public String getId() {
        return id != null ? id : "";
    }

    @NonNull
    public String getHotelId() {
        return hotelId != null ? hotelId : "";
    }

    @NonNull
    public String getName() {
        return name != null ? name : "";
    }

    @NonNull
    public String getDescription() {
        return description != null ? description : "";
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @NonNull
    public List<String> getImageUrls() {
        return imageUrls != null ? imageUrls : new ArrayList<>();
    }

    @NonNull
    public List<String> getRoomIds() {
        return roomIds != null ? roomIds : new ArrayList<>();
    }

    // Setter
    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setHotelId(@NonNull String hotelId) {
        this.hotelId = hotelId;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setImageUrls(@NonNull List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setRoomIds(@NonNull List<String> roomIds) {
        this.roomIds = roomIds;
    }

    // Optional: Builder Pattern
    public static class Builder {
        private String id;
        private String hotelId;
        private String name;
        private String description;
        private double pricePerNight;
        private List<String> imageUrls;
        private List<String> roomIds;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setHotelId(String hotelId) {
            this.hotelId = hotelId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPricePerNight(double pricePerNight) {
            this.pricePerNight = pricePerNight;
            return this;
        }

        public Builder setImageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
            return this;
        }

        public Builder setRoomIds(List<String> roomIds) {
            this.roomIds = roomIds;
            return this;
        }

        public RoomType build() {
            return new RoomType(id, hotelId, name, description, pricePerNight, imageUrls, roomIds);
        }
    }
}
