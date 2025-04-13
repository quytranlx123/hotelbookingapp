package com.example.hotelbookingapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.models.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;
    private Context context;
    private OnBookingClickListener listener;

    public interface OnBookingClickListener {
        void onBookingClick(Booking booking);
    }

    public BookingAdapter(Context context, List<Booking> bookings, OnBookingClickListener listener) {
        this.context = context;
        this.bookingList = bookings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.tvHotelName.setText(booking.getHotelName());
        holder.tvRoomType.setText(booking.getRoomType());
        holder.tvCheckIn.setText("Nhận phòng: " + booking.getCheckInDate());
        holder.tvCheckOut.setText("Trả phòng: " + booking.getCheckOutDate());

        holder.itemView.setOnClickListener(v -> listener.onBookingClick(booking));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvHotelName, tvRoomType, tvCheckIn, tvCheckOut;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHotelName = itemView.findViewById(R.id.tvHotelName);
            tvRoomType = itemView.findViewById(R.id.tvRoomType);
            tvCheckIn = itemView.findViewById(R.id.tvCheckIn);
            tvCheckOut = itemView.findViewById(R.id.tvCheckOut);
        }
    }
}
