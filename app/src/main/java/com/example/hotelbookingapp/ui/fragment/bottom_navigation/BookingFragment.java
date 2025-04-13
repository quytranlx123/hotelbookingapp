package com.example.hotelbookingapp.ui.fragment.bottom_navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.models.Booking;
import com.example.hotelbookingapp.ui.adapter.BookingAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends Fragment {

    private RecyclerView recyclerView;

    private List<Booking> mockBookingData() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking("1", "Khách sạn Majestic", "Deluxe View", "14/04/2025", "16/04/2025", 3600000));
        bookings.add(new Booking("2", "Vinpearl Resort", "Superior Sea View", "20/05/2025", "23/05/2025", 7200000));
        bookings.add(new Booking("3", "Pullman Saigon Centre", "Standard Room", "01/06/2025", "03/06/2025", 2400000));
        return bookings;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get reference to the RecyclerView after the view has been created
        recyclerView = view.findViewById(R.id.rvBookingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the adapter
        BookingAdapter adapter = new BookingAdapter(getContext(), mockBookingData(), booking -> {
            Bundle bundle = new Bundle();
            bundle.putString("hotelName", booking.getHotelName());
            bundle.putString("roomType", booking.getRoomType());
            bundle.putString("checkInDate", booking.getCheckInDate());
            bundle.putString("checkOutDate", booking.getCheckOutDate());
            bundle.putDouble("totalPrice", booking.getTotalPrice());

            // Navigate to BookingDetailFragment
            Navigation.findNavController(view).navigate(R.id.action_bookingFragment_to_bookingDetailFragment, bundle);
        });

        // Set the adapter for RecyclerView
        recyclerView.setAdapter(adapter);
    }
}
