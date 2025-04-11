//package com.example.hotelbookingapp.ui.fragment.management;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.example.hotelbookingapp.R;
//
//
//public class RoomDetailFragment extends Fragment {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_room_detail, container, false);
//    }
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        String hotelName = requireArguments().getString("hotelName");
//        String imageUrl = requireArguments().getString("imageUrl");
//
//        // set dữ liệu lên UI
//        TextView nameTextView = view.findViewById(R.id.txt_hotel_name);
//        ImageView imageView = view.findViewById(R.id.img_hotel);
//
//        nameTextView.setText(hotelName);
//        Glide.with(requireContext()).load(imageUrl).into(imageView);
//    }
//}