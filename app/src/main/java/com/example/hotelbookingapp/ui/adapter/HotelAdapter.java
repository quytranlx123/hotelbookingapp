package com.example.hotelbookingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.models.Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder>  {
    private List<Hotel> hotelList = new ArrayList<>();

    private final OnHotelClickListener listener;

    public HotelAdapter(OnHotelClickListener listener) {
        this.listener = listener;
    }

    public void setHotelList(List<Hotel> hotels) {
        this.hotelList = hotels;
        notifyDataSetChanged();  // Báo RecyclerView cập nhật
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hotel, parent, false);   // inflate XML của item
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.bind(hotel);
        holder.itemView.setOnClickListener(v -> listener.onHotelClick(hotel));  // <- truyền dữ liệu khi click
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvHotelName;
//        private final TextView tvHotelRating;
//        private final TextView tvHotelAddress;
        private final ImageView ivHotelImage;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHotelName = itemView.findViewById(R.id.hotel_name);
            ivHotelImage = itemView.findViewById(R.id.hotel_image);
//            tvHotelRating = itemView.findViewById(R.id.tvHotelRating);
        }

        public void bind(Hotel hotel) {
            tvHotelName.setText(hotel.getName());
            Glide.with(itemView.getContext())
                    .load(hotel.getImageUrl())
                    .placeholder(R.drawable.hotelbg)
                    .into(ivHotelImage);
        }
    }
    public interface OnHotelClickListener {
        void onHotelClick(Hotel hotel);
    }

}
