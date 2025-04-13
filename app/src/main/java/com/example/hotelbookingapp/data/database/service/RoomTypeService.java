package com.example.hotelbookingapp.data.database.service;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.hotelbookingapp.data.models.RoomType;

import java.util.List;

public class RoomTypeService {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference roomTypesCollection = db.collection("room_types");  // Tham chiếu đến collection room_types

    // Thêm RoomType vào Firestore
    public void addRoomType(RoomType roomType, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        roomTypesCollection.add(roomType)
                .addOnSuccessListener(documentReference -> {
                    // Here, you can handle the document reference if needed
                    onSuccessListener.onSuccess(null);  // Since the original listener expects Void, pass null
                })
                .addOnFailureListener(onFailureListener);
    }

    // Sửa RoomType theo ID
    public void updateRoomType(String roomTypeId, RoomType roomType, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        DocumentReference roomTypeRef = roomTypesCollection.document(roomTypeId);
        roomTypeRef.set(roomType)  // Sử dụng `set` để ghi đè dữ liệu
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    // Xoá RoomType theo ID
    public void deleteRoomType(String roomTypeId, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        DocumentReference roomTypeRef = roomTypesCollection.document(roomTypeId);
        roomTypeRef.delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    // Lấy tất cả RoomTypes
    public void getAllRoomTypes(OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        roomTypesCollection.get()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    // Lấy RoomType theo ID
    public void getRoomTypeById(String roomTypeId, OnSuccessListener<DocumentSnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        DocumentReference roomTypeRef = roomTypesCollection.document(roomTypeId);
        roomTypeRef.get()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
}