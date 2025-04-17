package com.example.hotelbookingapp.ui.fragment.management;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hotelbookingapp.R;

public class DashboardFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Tìm các CardView trong layout
        Button btnHotelManagement = view.findViewById(R.id.btnHotelManagement);
        Button btnRoomManagement = view.findViewById(R.id.btnRoomManagement);
        CardView roomTypeCardView = view.findViewById(R.id.roomTypeCardView);

        // Lấy NavController từ view
        NavController navController = Navigation.findNavController(view);

        // Thiết lập sự kiện nhấn cho CardView "Phòng"
        btnRoomManagement.setOnClickListener(v -> {
            // Xử lý khi người dùng nhấn vào CardView Phòng
            navController.navigate(R.id.action_dashboardFragment_to_roomManagementFragment);
        });

        // Thiết lập sự kiện nhấn cho CardView "Loại Phòng"
        roomTypeCardView.setOnClickListener(v -> {
            // Xử lý khi người dùng nhấn vào CardView Loại Phòng
            navController.navigate(R.id.action_dashboardFragment_to_roomTypeManagementFragment);
        });

        // Thiết lập sự kiện nhấn cho CardView "Khách Sạn"
        btnHotelManagement.setOnClickListener(v -> {
            // Xử lý khi người dùng nhấn vào CardView Khách Sạn
            navController.navigate(R.id.action_dashboardFragment_to_hotelManagementFragment);
        });
    }
}
