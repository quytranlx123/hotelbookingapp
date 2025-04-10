package com.example.hotelbookingapp.ui.fragment.management;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.database.service.FirestoreService;
import com.example.hotelbookingapp.data.models.Hotel;
import com.example.hotelbookingapp.ui.adapter.HotelAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private RecyclerView recyclerView;
    FirestoreService firestoreService = new FirestoreService();
    private List<Hotel> hotelList = new ArrayList<>();
    private HotelAdapter adapter;
    private Hotel selectedHotel = null;
    private ImageView imagePreview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewHotels);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HotelAdapter(hotelList, hotel -> {
            selectedHotel = hotel;
            Toast.makeText(getContext(), "Đã chọn: " + hotel.getName(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        loadHotels();

        btnAdd.setOnClickListener(v -> {
            selectedImageUri = null;
            showHotelDialog(null);
        });

        btnEdit.setOnClickListener(v -> {
            if (selectedHotel != null) {
                selectedImageUri = null;
                showHotelDialog(selectedHotel);
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (selectedHotel != null) deleteHotel(selectedHotel.getId());
        });

        return view;
    }

    private void loadHotels() {
        firestoreService.getAllHotels(hotels -> {
            hotelList.clear();
            hotelList.addAll(hotels);
            adapter.notifyDataSetChanged();
        });
    }

    private void showHotelDialog(Hotel hotel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit_hotel, null);
        builder.setView(dialogView);
        builder.setTitle(hotel == null ? "Thêm khách sạn mới" : "Chỉnh sửa khách sạn");

        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etAddress = dialogView.findViewById(R.id.etAddress);
        EditText etPrice = dialogView.findViewById(R.id.etPrice);
        EditText etRating = dialogView.findViewById(R.id.etRating);
        Button btnSelectImage = dialogView.findViewById(R.id.btnSelectImage);

        if (hotel != null) {
            etName.setText(hotel.getName());
            etAddress.setText(hotel.getAddress());
            etPrice.setText(String.valueOf(hotel.getPrice()));
            etRating.setText(String.valueOf(hotel.getRating()));
            ImageView imgPreview = dialogView.findViewById(R.id.imagePreview);
            Glide.with(this)
                    .load(hotel.getImageUrl())
                    .placeholder(R.drawable.hotel) // ảnh tạm nếu URL null
                    .error(R.drawable.hotel) // ảnh lỗi nếu load thất bại
                    .into(imgPreview);
        }

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        builder.setPositiveButton(hotel == null ? "Thêm" : "Cập nhật", null);
        builder.setNegativeButton("Huỷ", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();
            String ratingStr = etRating.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty() || priceStr.isEmpty() || ratingStr.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                float rating = Float.parseFloat(ratingStr);

                if (price <= 0 || rating < 0 || rating > 5) {
                    Toast.makeText(getContext(), "Giá phải > 0 và đánh giá từ 0-5!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (hotel == null || selectedImageUri != null) {
                    if (selectedImageUri == null) {
                        Toast.makeText(getContext(), "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    uploadImageToCloudinary(selectedImageUri, imageUrl -> {
                        if (hotel == null) {
                            Hotel newHotel = new Hotel(null, name, address, price, rating, imageUrl);
                            addHotelToFirestore(newHotel);
                        } else {
                            hotel.setName(name);
                            hotel.setAddress(address);
                            hotel.setPrice(price);
                            hotel.setRating(rating);
                            hotel.setImageUrl(imageUrl);
                            updateHotelInFirestore(hotel);
                        }
                        selectedImageUri = null;
                        dialog.dismiss();
                    });

                } else {
                    hotel.setName(name);
                    hotel.setAddress(address);
                    hotel.setPrice(price);
                    hotel.setRating(rating);
                    updateHotelInFirestore(hotel);
                    selectedImageUri = null;
                    dialog.dismiss();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (imagePreview != null) {
                imagePreview.setImageURI(selectedImageUri);
            }
            Toast.makeText(getContext(), "Đã chọn ảnh!", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnImageUploadedListener {
        void onUploaded(String imageUrl);
    }

    private void uploadImageToCloudinary(Uri imageUri, OnImageUploadedListener listener) {
        MediaManager.get().upload(imageUri)
                .callback(new UploadCallback() {
                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        String imageUrl = resultData.get("secure_url").toString();
                        listener.onUploaded(imageUrl);
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Toast.makeText(getContext(), "Lỗi khi upload ảnh", Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onStart(String requestId) {}
                    @Override public void onProgress(String requestId, long bytes, long totalBytes) {}
                    @Override public void onReschedule(String requestId, ErrorInfo error) {}
                }).dispatch();
    }

    private void addHotelToFirestore(Hotel hotel) {
        firestoreService.addHotel(hotel, unused -> {
            Toast.makeText(getContext(), "Đã thêm!", Toast.LENGTH_SHORT).show();
            loadHotels();
        });
    }

    private void updateHotelInFirestore(Hotel hotel) {
        firestoreService.updateHotel(hotel, unused -> {
            Toast.makeText(getContext(), "Đã cập nhật!", Toast.LENGTH_SHORT).show();
            loadHotels();
        });
    }

    private void deleteHotel(String id) {
        firestoreService.deleteHotel(id, unused -> {
            Toast.makeText(getContext(), "Đã xoá!", Toast.LENGTH_SHORT).show();
            selectedHotel = null;
            loadHotels();
        });
    }
}
