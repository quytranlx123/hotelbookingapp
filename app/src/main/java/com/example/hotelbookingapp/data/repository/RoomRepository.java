package com.example.hotelbookingapp.data.repository;

import android.content.Context;
import android.widget.Toast;


import com.example.hotelbookingapp.data.models.Room;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.*;
import java.util.*;

public class RoomRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Thêm phòng
    public void addRoom(Room room, Context context) {
        db.collection("rooms")
                .document(room.getId())
                .set(room)
                .addOnSuccessListener(unused ->
                        Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // Lấy danh sách phòng
    public void getAllRooms(OnSuccessListener<List<Room>> listener, OnFailureListener failureListener) {
        db.collection("rooms").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Room> roomList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Room room = doc.toObject(Room.class);
                        roomList.add(room);
                    }
                    listener.onSuccess(roomList);
                })
                .addOnFailureListener(failureListener);
    }

    // Cập nhật phòng
    public void updateRoom(Room room, Context context) {
        db.collection("rooms")
                .document(room.getId())
                .update("status", room.getStatus(), "price", room.getPrice())
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Cập nhật OK", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Lỗi cập nhật: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // Xoá phòng
    public void deleteRoom(String roomId, Context context) {
        db.collection("rooms")
                .document(roomId)
                .delete()
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Lỗi xoá: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
