package com.example.hotelbookingapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hotelbookingapp.data.models.Hotel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HotelRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public LiveData<List<Hotel>> getHotels() {
        MutableLiveData<List<Hotel>> hotelsLiveData = new MutableLiveData<>();

        db.collection("hotels")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Hotel> hotelList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Hotel hotel = doc.toObject(Hotel.class);
                        hotelList.add(hotel);
                    }
                    hotelsLiveData.setValue(hotelList);
                })
                .addOnFailureListener(e -> {
                    hotelsLiveData.setValue(null); // or handle error as needed
                });

        return hotelsLiveData;
    }

}
