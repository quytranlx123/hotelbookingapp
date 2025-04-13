package com.example.hotelbookingapp.ui.fragment.management;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.database.service.RoomTypeService;
import com.example.hotelbookingapp.data.models.RoomType;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RoomTypeManagementFragment extends Fragment {

    private RoomTypeService roomTypeService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomTypeService = new RoomTypeService(); // Khởi tạo service
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_type_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btnAddRoomType = view.findViewById(R.id.btnAddRoomType);
        btnAddRoomType.setOnClickListener(v -> showAddEditDialog(getContext(), null)); // Truyền null khi thêm mới
    }

    public void showAddEditDialog(Context context, @Nullable String roomTypeId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_add_edit_room_type, null);

        TextInputEditText etRoomTypeId = view.findViewById(R.id.etRoomTypeId);  // Lấy trường nhập ID
        TextInputEditText etRoomTypeName = view.findViewById(R.id.etRoomTypeName);
        TextInputEditText etRoomTypeDescription = view.findViewById(R.id.etRoomTypeDescription);
        TextInputEditText etRoomTypePrice = view.findViewById(R.id.etRoomTypePrice);
        Spinner spinnerHotel = view.findViewById(R.id.spinnerHotel);
        ImageView ivRoomTypeImage = view.findViewById(R.id.ivRoomType);

        MaterialButton btnSave = view.findViewById(R.id.btnSaveRoomType);
        MaterialButton btnCancel = view.findViewById(R.id.btnCancelRoomType);

        ivRoomTypeImage.setOnClickListener(v -> openImagePicker());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();

        List<String> hotelIdList = new ArrayList<>();
        ArrayAdapter<String> hotelAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, new ArrayList<>()
        );
        hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHotel.setAdapter(hotelAdapter);

        // Tải danh sách khách sạn lên spinner
        FirebaseFirestore.getInstance().collection("hotels")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    hotelIdList.clear();
                    List<String> hotelNames = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String name = doc.getString("name");
                        if (name != null) {
                            hotelIdList.add(doc.getId());
                            hotelNames.add(name);
                        }
                    }
                    hotelAdapter.clear();
                    hotelAdapter.addAll(hotelNames);
                    hotelAdapter.notifyDataSetChanged();

                    // Nếu đang sửa, load dữ liệu room type lên
                    if (!TextUtils.isEmpty(roomTypeId)) {
                        roomTypeService.getRoomTypeById(roomTypeId, documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                RoomType roomType = documentSnapshot.toObject(RoomType.class);
                                if (roomType != null) {
                                    etRoomTypeId.setText(roomType.getId());  // Set ID từ dữ liệu Firestore
                                    etRoomTypeName.setText(roomType.getName());
                                    etRoomTypeDescription.setText(roomType.getDescription());
                                    etRoomTypePrice.setText(String.valueOf(roomType.getPricePerNight()));

                                    if (!roomType.getImageUrls().isEmpty()) {
                                        Glide.with(context)
                                                .load(roomType.getImageUrls().get(0))
                                                .into(ivRoomTypeImage);
                                    }

                                    int index = hotelIdList.indexOf(roomType.getHotelId());
                                    if (index != -1) {
                                        spinnerHotel.setSelection(index);
                                    }
                                }
                            }
                        }, e -> Toast.makeText(context, "Lỗi khi tải thông tin loại phòng!", Toast.LENGTH_SHORT).show());
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Lỗi tải danh sách khách sạn!", Toast.LENGTH_SHORT).show());

        btnSave.setOnClickListener(v -> {
            // Sử dụng tham số roomTypeId đã được truyền vào phương thức
            String id = etRoomTypeId.getText().toString().trim();  // Lấy ID người dùng nhập vào (không khai báo lại biến roomTypeId)

            String name = etRoomTypeName.getText().toString().trim();
            String description = etRoomTypeDescription.getText().toString().trim();
            String priceString = etRoomTypePrice.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(priceString)) {
                Toast.makeText(context, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedPosition = spinnerHotel.getSelectedItemPosition();
            if (selectedPosition < 0 || selectedPosition >= hotelIdList.size()) {
                Toast.makeText(context, "Vui lòng chọn khách sạn!", Toast.LENGTH_SHORT).show();
                return;
            }

            String hotelId = hotelIdList.get(selectedPosition);
            double pricePerNight = Double.parseDouble(priceString);
            List<String> imageUrls = new ArrayList<>();  // Có thể thêm ảnh sau khi upload
            List<String> roomIds = new ArrayList<>();    // Có thể set sau nếu có room cụ thể

            // Tạo đối tượng RoomType
            RoomType roomType = new RoomType.Builder()
                    .setId(id)  // Sử dụng ID từ trường nhập, thay vì roomTypeId tham số
                    .setHotelId(hotelId)
                    .setName(name)
                    .setDescription(description)
                    .setPricePerNight(pricePerNight)
                    .setImageUrls(imageUrls)
                    .setRoomIds(roomIds)
                    .build();

            // Kiểm tra xem ID có tồn tại không
            if (!TextUtils.isEmpty(id)) {
                // Nếu ID có giá trị, tiến hành update
                roomTypeService.updateRoomType(id, roomType, unused -> {
                    Toast.makeText(context, "Cập nhật loại phòng thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }, e -> {
                    Toast.makeText(context, "Lỗi khi cập nhật loại phòng!", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Nếu ID trống, thêm mới và Firestore tự động sinh ID
                roomTypeService.addRoomType(roomType, unused -> {
                    Toast.makeText(context, "Thêm loại phòng thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }, e -> {
                    Toast.makeText(context, "Lỗi khi thêm loại phòng!", Toast.LENGTH_SHORT).show();
                });
            }
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void openImagePicker() {
        // Code mở gallery / Cloudinary upload sẽ để ở đây
    }
}
