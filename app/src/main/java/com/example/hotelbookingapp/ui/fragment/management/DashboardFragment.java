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
        // Thực hiện các thao tác giao diện ở đây
        CardView roomCardView = view.findViewById(R.id.roomCardView);
        CardView roomTypeCardView = view.findViewById(R.id.roomTypeCardView);
        CardView userCardView = view.findViewById(R.id.userCardView);
        CardView bookingCardView = view.findViewById(R.id.bookingCardView);
        CardView commentCardView = view.findViewById(R.id.commentCardView);
        CardView paymentCardView = view.findViewById(R.id.paymentCardView);
        CardView hotelCardView = view.findViewById(R.id.hotelCardView);
        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(android.R.anim.slide_in_left)
                .setExitAnim(android.R.anim.slide_out_right)
                .setPopEnterAnim(android.R.anim.slide_in_left)
                .setPopExitAnim(android.R.anim.slide_out_right)
                .build();

        NavController navController = Navigation.findNavController(view);
        roomCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng
                navController.navigate(R.id.action_dashboardFragment_to_roomManagementFragment, null, navOptions);
            }
        });

        roomTypeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng
                navController.navigate(R.id.action_dashboardFragment_to_roomTypeManagementFragment, null, navOptions);

            }
        });
        userCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng
                navController.navigate(R.id.action_dashboardFragment_to_userManagementFragment, null, navOptions);

            }
        });
        bookingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng
                navController.navigate(R.id.action_dashboardFragment_to_bookingManagementFragment, null, navOptions);
            }
        });
        commentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng
                navController.navigate(R.id.action_dashboardFragment_to_commentManagementFragment, null, navOptions);

            }
        });
        paymentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng
                navController.navigate(R.id.action_dashboardFragment_to_paymentManagementFragment, null, navOptions);

            }
        });
        hotelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_dashboardFragment_to_hotelManagementFragment, null, navOptions);
            }
        });

    }
}