package com.example.hotelbookingapp.ui.fragment.client;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hotelbookingapp.R;

public class HotelDetailFragment extends Fragment {

    public HotelDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nameTextView = view.findViewById(R.id.tvHotelName);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String hotelName = bundle.getString("hotelName");
            nameTextView.setText(hotelName);
        }
    }
}
