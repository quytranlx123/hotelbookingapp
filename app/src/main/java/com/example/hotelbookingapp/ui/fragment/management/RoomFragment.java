//package com.example.hotelbookingapp.ui.fragment;
//
//import android.app.AlertDialog;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.appcompat.widget.SearchView;
//
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.hotelbookingapp.R;
//import com.example.hotelbookingapp.data.models.Room;
//import com.example.hotelbookingapp.ui.adapter.RoomAdapter;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class RoomFragment extends Fragment {
//
//    private RecyclerView rvRooms;
//    private SearchView searchView;
//
//    private FirebaseFirestore db;
//    private List<Room> roomList;
//    private RoomAdapter adapter;
//
//    public RoomFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_room, container, false);
//        rvRooms = view.findViewById(R.id.rvRooms);
//        Button btnAdd = view.findViewById(R.id.btnAdd);
//        searchView = view.findViewById(R.id.searchView);
//
//        db = FirebaseFirestore.getInstance();
//        roomList = new ArrayList<>();
//        adapter = new RoomAdapter(roomList);
//        rvRooms.setLayoutManager(new LinearLayoutManager(getContext()));
//        rvRooms.setAdapter(adapter);
//
//        loadRooms();
//        handleSearch();
//
//        btnAdd.setOnClickListener(v -> showRoomDialog(null));
//
//        return view;
//    }
//
//    private void loadRooms() {
//        db.collection("rooms").get().addOnSuccessListener(queryDocumentSnapshots -> {
//            roomList.clear();
//            for (DocumentSnapshot doc : queryDocumentSnapshots) {
//                Room room = doc.toObject(Room.class);
//                room.setId(doc.getId());
//                roomList.add(room);
//            }
//            adapter.notifyDataSetChanged();
//        });
//    }
//
//    private void handleSearch() {
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                List<Room> filtered = new ArrayList<>();
//                for (Room r : roomList) {
//                    if (r.getRoomNumber().toLowerCase().contains(newText.toLowerCase())) {
//                        filtered.add(r);
//                    }
//                }
//                adapter = new RoomAdapter(filtered, getContext(), RoomFragment.this::showRoomOptionsDialog);
//                rvRooms.setAdapter(adapter);
//                return true;
//            }
//        });
//    }
//
//    private void showRoomOptionsDialog(Room room) {
//        new AlertDialog.Builder(getContext())
//                .setTitle("Tùy chọn")
//                .setItems(new CharSequence[]{"Sửa", "Xoá"}, (dialog, which) -> {
//                    if (which == 0) {
//                        showRoomDialog(room);
//                    } else {
//                        db.collection("rooms").document(room.getId()).delete().addOnSuccessListener(unused -> {
//                            Toast.makeText(getContext(), "Đã xoá", Toast.LENGTH_SHORT).show();
//                            loadRooms();
//                        });
//                    }
//                }).show();
//    }
//
//    private void showRoomDialog(Room room) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle(room == null ? "Thêm phòng" : "Sửa phòng");
//
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_room, null);
//        EditText edtRoomNumber = view.findViewById(R.id.edtRoomNumber);
//        EditText edtRoomType = view.findViewById(R.id.edtRoomType);
//        EditText edtPrice = view.findViewById(R.id.edtPrice);
//        EditText edtStatus = view.findViewById(R.id.edtStatus);
//        EditText edtImageUrl = view.findViewById(R.id.edtImageUrl);
//
//        if (room != null) {
//            edtRoomNumber.setText(room.getRoomNumber());
//            edtRoomType.setText(room.getRoomType());
//            edtPrice.setText(String.valueOf(room.getPrice()));
//            edtStatus.setText(room.getStatus());
//            edtImageUrl.setText(room.getImageUrl());
//        }
//
//        builder.setView(view);
//        builder.setPositiveButton("Lưu", (dialog, which) -> {
//            String number = edtRoomNumber.getText().toString();
//            String type = edtRoomType.getText().toString();
//            String price = edtPrice.getText().toString();
//            String status = edtStatus.getText().toString();
//            String imgUrl = edtImageUrl.getText().toString();
//
//            if (room == null) {
//                Room newRoom = new Room("", number, type, price, status, imgUrl);
//                db.collection("rooms").add(newRoom).addOnSuccessListener(documentReference -> {
//                    Toast.makeText(getContext(), "Đã thêm", Toast.LENGTH_SHORT).show();
//                    loadRooms();
//                });
//            } else {
//                Map<String, Object> updates = new HashMap<>();
//                updates.put("roomNumber", number);
//                updates.put("roomType", type);
//                updates.put("price", price);
//                updates.put("status", status);
//                updates.put("imageUrl", imgUrl);
//
//                db.collection("rooms").document(room.getId()).update(updates).addOnSuccessListener(unused -> {
//                    Toast.makeText(getContext(), "Đã cập nhật", Toast.LENGTH_SHORT).show();
//                    loadRooms();
//                });
//            }
//        });
//        builder.setNegativeButton("Huỷ", null);
//        builder.show();
//    }
//}