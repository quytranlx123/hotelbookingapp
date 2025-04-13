package com.example.hotelbookingapp.ui.fragment.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hotelbookingapp.R;

public class BookingDetailFragment extends Fragment {

    private TextView tvHotelName, tvRoomType, tvCheckIn, tvCheckOut, tvTotalPrice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No need to set the TextViews here, do it after inflation in onCreateView.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_detail, container, false);

        // Initialize the TextViews after the view is inflated
        tvHotelName = view.findViewById(R.id.tvHotelName);
        tvRoomType = view.findViewById(R.id.tvRoomType);
        tvCheckIn = view.findViewById(R.id.tvCheckin);
        tvCheckOut = view.findViewById(R.id.tvCheckout);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);

        // Retrieve data from the Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String hotelName = bundle.getString("hotelName");
            String roomType = bundle.getString("roomType");
            String checkIn = bundle.getString("checkInDate");
            String checkOut = bundle.getString("checkOutDate");
            double totalPrice = bundle.getDouble("totalPrice");

            // Set the text for each TextView
            tvHotelName.setText(hotelName);
            tvRoomType.setText(roomType);
            tvCheckIn.setText(checkIn);
            tvCheckOut.setText(checkOut);
            tvTotalPrice.setText(String.format("%,.0f VND", totalPrice));
        }

        return view;
    }
}
