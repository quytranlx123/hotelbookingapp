package com.example.hotelbookingapp.data.database.service;

import com.example.hotelbookingapp.data.models.Hotel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirestoreService {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getAllHotels(OnSuccessListener<List<Hotel>> listener) {
        db.collection("hotels")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Hotel> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        Hotel hotel = doc.toObject(Hotel.class);
                        hotel.setId(doc.getId());
                        list.add(hotel);
                    }
                    listener.onSuccess(list);
                });
    }

    public void addHotel(Hotel hotel, OnSuccessListener<Void> listener) {
        db.collection("hotels")
                .add(hotel)
                .addOnSuccessListener(docRef -> listener.onSuccess(null));
    }

    public void updateHotel(Hotel hotel, OnSuccessListener<Void> listener) {
        db.collection("hotels")
                .document(hotel.getId())
                .set(hotel)
                .addOnSuccessListener(listener);
    }

    public void deleteHotel(String hotelId, OnSuccessListener<Void> listener) {
        db.collection("hotels")
                .document(hotelId)
                .delete()
                .addOnSuccessListener(listener);
    }
}
