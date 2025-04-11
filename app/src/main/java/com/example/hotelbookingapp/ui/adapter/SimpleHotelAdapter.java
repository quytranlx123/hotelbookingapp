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

public class SimpleHotelAdapter extends RecyclerView.Adapter<SimpleHotelAdapter.ViewHolder> {
    private Context context;
    private List<SimpleHotel> hotelList;

    public SimpleHotelAdapter(Context context, List<SimpleHotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleHotel hotel = hotelList.get(position);
        holder.name.setText(hotel.getName());
        Glide.with(context).load(hotel.getImageUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.hotel_image);
            name = itemView.findViewById(R.id.hotel_name);
        }
    }
}