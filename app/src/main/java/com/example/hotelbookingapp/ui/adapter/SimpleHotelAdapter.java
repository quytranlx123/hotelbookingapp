package com.example.hotelbookingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.models.SimpleHotel;

import java.util.List;

public class SimpleHotelAdapter extends RecyclerView.Adapter<SimpleHotelAdapter.HotelViewHolder> {

    public interface OnHotelClickListener {
        void onHotelClick(SimpleHotel hotel);
    }

    private Context context;
    private List<SimpleHotel> hotelList;
    private OnHotelClickListener listener;

    public SimpleHotelAdapter(Context context, List<SimpleHotel> hotelList, OnHotelClickListener listener) {
        this.context = context;
        this.hotelList = hotelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        SimpleHotel hotel = hotelList.get(position);
        holder.nameTextView.setText(hotel.getName());
        // Load ảnh bằng Glide hoặc Picasso
        Glide.with(context).load(hotel.getImageUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onHotelClick(hotel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.hotel_name);
            imageView = itemView.findViewById(R.id.hotel_image);
        }
    }
}
