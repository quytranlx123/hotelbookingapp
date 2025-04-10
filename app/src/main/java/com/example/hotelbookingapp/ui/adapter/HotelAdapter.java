package com.example.hotelbookingapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.models.Hotel;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private List<Hotel> hotelList;
    private OnHotelClickListener listener;

    public void setHotelList(List<Hotel> hotelList) {
        this.hotelList = hotelList;
        notifyDataSetChanged();
    }

    public interface OnHotelClickListener {
        void onItemClick(Hotel hotel);
    }

    public HotelAdapter(List<Hotel> hotelList, OnHotelClickListener listener) {
        this.hotelList = hotelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_management, parent, false);
        return new HotelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.tvName.setText(hotel.getName());
        holder.tvAddress.setText(hotel.getAddress());
        holder.tvPrice.setText("Giá: " + hotel.getPrice());
        holder.tvRating.setText("⭐ " + hotel.getRating());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(hotel));
    }

    @Override
    public int getItemCount() {
        if (hotelList == null) {
            return hotelList.size();
        }
        return 0;
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvPrice, tvRating;
        ImageView imgHotel;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvHotelName);
            tvAddress = itemView.findViewById(R.id.tvHotelAddress);
            tvPrice = itemView.findViewById(R.id.tvHotelPrice);
            tvRating = itemView.findViewById(R.id.tvHotelRating);
        }
    }
}
