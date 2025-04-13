package com.example.hotelbookingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.models.Hotel;

import java.util.List;

public class FavoriteHotelAdapter extends RecyclerView.Adapter<FavoriteHotelAdapter.HotelViewHolder> {

    private List<Hotel> hotelList;
    private Context context;

    public FavoriteHotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.tvHotelName.setText(hotel.getName());
        holder.tvHotelPrice.setText(hotel.getPrice() + " đ/đêm");

        Glide.with(context).load(hotel.getImageUrl())
                .placeholder(R.drawable.hotelbg)
                .into(holder.imgHotel);

        holder.ivFavorite.setOnClickListener(v -> {
            // Xử lý bỏ yêu thích
            Toast.makeText(context, "Đã bỏ khỏi yêu thích!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHotel, ivFavorite;
        TextView tvHotelName, tvHotelPrice;

        HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHotel = itemView.findViewById(R.id.imgHotel);
            tvHotelName = itemView.findViewById(R.id.tvHotelName);
            tvHotelPrice = itemView.findViewById(R.id.tvHotelPrice);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
        }
    }
}

