package com.example.hotelbookingapp.ui.fragment.bottom_navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.models.SimpleHotel;
import com.example.hotelbookingapp.ui.adapter.HotelAdapter;
import com.example.hotelbookingapp.ui.viewmodel.HotelViewModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private HotelAdapter adapter;
    private List<SimpleHotel> hotelList;
    private HotelViewModel hotelViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_horizontal_hotels); // sửa từ findViewById thành view.findViewById
        hotelViewModel = new ViewModelProvider(requireActivity()).get(HotelViewModel.class);

        HotelAdapter hotelAdapter = new HotelAdapter(hotel -> {
            // khi click vào item, truyền dữ liệu sang Fragment khác

            Bundle bundle = new Bundle();
            bundle.putSerializable("hotel", hotel);  // nhớ model Hotel implements Serializable hoặc Parcelable

            NavHostFragment.findNavController(this).navigate(
                    R.id.action_searchFragment_to_hotelDetailFragment, bundle
            );
        });

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        recyclerView.setAdapter(hotelAdapter);


        hotelViewModel.getHotels().observe(getViewLifecycleOwner(), hotels -> {
            if (hotels != null) {
                // Cập nhật RecyclerView ở đây
                Toast.makeText(requireContext(), "Có khách sạn nha", Toast.LENGTH_SHORT).show();
                hotelAdapter.setHotelList(hotels);
            } else {
                Toast.makeText(requireContext(), "Lỗi khi tải khách sạn!", Toast.LENGTH_SHORT).show();
            }
        });


//        hotelList = new ArrayList<>();
//        adapter = new SimpleHotelAdapter(requireContext(), hotelList, hotel -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("hotelName", hotel.getName());
//
//            NavController navController = Navigation.findNavController(requireView());
//            navController.navigate(R.id.action_searchFragment_to_hotelDetailFragment, bundle);
//        });

//        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.setAdapter(adapter);
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("hotels")
//                .get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
//                        String name = doc.getString("name");
//                        String imageUrl = doc.getString("imageUrl");
//                        hotelList.add(new SimpleHotel(name, imageUrl));
//                    }
//                    adapter.notifyDataSetChanged();
//                })
//                .addOnFailureListener(e -> {
//                    Log.e("Firebase", "Lỗi tải dữ liệu: " + e.getMessage());
//                });
    }
}