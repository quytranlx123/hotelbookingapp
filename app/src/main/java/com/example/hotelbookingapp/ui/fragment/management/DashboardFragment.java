package com.example.hotelbookingapp.ui.fragment.management;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
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
        NavController navController = Navigation.findNavController(view);

        roomCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng
                navController.navigate(R.id.action_dashboardFragment_to_roomFragment);
            }
        });

        roomTypeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng

            }
        });
        userCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng

            }
        });
        bookingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng

            }
        });
        commentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng

            }
        });
        paymentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào CardView Phòng

            }
        });
        hotelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_dashboardFragment_to_hotelFragment);
            }
        });

    }
}