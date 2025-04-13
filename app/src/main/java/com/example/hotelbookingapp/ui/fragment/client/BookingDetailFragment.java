package com.example.hotelbookingapp.ui.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotelbookingapp.R;

public class BookingDetailFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String hotelName = bundle.getString("hotelName");
            String roomType = bundle.getString("roomType");
            String checkIn = bundle.getString("checkInDate");
            String checkOut = bundle.getString("checkOutDate");
            double totalPrice = bundle.getDouble("totalPrice");

            tvHotelName.setText(hotelName);
            tvRoomType.setText(roomType);
            tvCheckIn.setText(checkIn);
            tvCheckOut.setText(checkOut);
            tvTotalPrice.setText(String.format("%,.0f VND", totalPrice));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_detail, container, false);
    }
}