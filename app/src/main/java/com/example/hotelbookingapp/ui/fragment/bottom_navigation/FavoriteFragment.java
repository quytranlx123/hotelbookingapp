package com.example.hotelbookingapp.ui.fragment.bottom_navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.models.Hotel;
import com.example.hotelbookingapp.ui.adapter.FavoriteHotelAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView rvFavoriteHotels;
    private FavoriteHotelAdapter adapter;
    private List<Hotel> favoriteHotels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rvFavoriteHotels = view.findViewById(R.id.rvFavoriteHotels);

        favoriteHotels = new ArrayList<>();
        // giả lập data
        favoriteHotels.add(new Hotel("Vinpearl Resort", "https://yourcdn.com/vinpearl.jpg", "1.250.000"));
        favoriteHotels.add(new Hotel("Melia Hanoi", "https://yourcdn.com/melia.jpg", "2.500.000"));

        adapter = new FavoriteHotelAdapter(getContext(), favoriteHotels);
        rvFavoriteHotels.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavoriteHotels.setAdapter(adapter);

        return view;
    }
}
