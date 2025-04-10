package com.example.hotelbookingapp.ui.adapter;

import android.content.Context;
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

import java.util.List;

public class HotelManagementAdapter extends RecyclerView.Adapter<HotelManagementAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Hotel hotel);
    }

    private List<Hotel> hotelList;
    private OnItemClickListener onItemClickListener;

    public HotelManagementAdapter(List<Hotel> hotelList, OnItemClickListener listener) {
        this.hotelList = hotelList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.bind(hotel);
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, price, rating;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hotel_name);
            address = itemView.findViewById(R.id.hotel_address);
            price = itemView.findViewById(R.id.hotel_price);
            rating = itemView.findViewById(R.id.hotel_rating);
        }

        void bind(Hotel hotel) {
            name.setText(hotel.getName());
            address.setText(hotel.getAddress());
            price.setText("Giá: " + hotel.getPrice());
            rating.setText("Đánh giá: " + hotel.getRating());

            itemView.setOnClickListener(v -> {
                onItemClickListener.onItemClick(hotel);
            });
        }
    }
}

