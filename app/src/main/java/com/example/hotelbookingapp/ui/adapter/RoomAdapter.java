//package com.example.hotelbookingapp.ui.adapter;
//
//
//import android.content.Context;
//import android.provider.ContactsContract;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.hotelbookingapp.R;
//import com.example.hotelbookingapp.data.models.Room;
//
//import java.util.List;
//
//public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
//    private List<Room> roomList;
//    private Context context;
//
//    public void setRoomList(List<Room> roomList) {
//        this.roomList = roomList;
//        notifyDataSetChanged();
//    }
//
//
//    @NonNull
//    @Override
//    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
//        return new RoomViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
//        Room room = roomList.get(position);
//        if (room == null) {
//            return;
//        }
//
//        Glide.with(holder.itemView.getContext())
//                .load(room.getImageUrl()) // getImageUrl() trả về String (URL hoặc tên file)
//                .into(holder.imageRoom);
//    }
//
//    @Override
//    public int getItemCount() {
//        if (roomList != null) {
//            return roomList.size();
//        }
//        return 0;
//    }
//
//    public class RoomViewHolder extends RecyclerView.ViewHolder {
//        private ImageView imageRoom;
//        private TextView tvRoomType;
//
//        public RoomViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imageRoom = itemView.findViewById(R.id.imgRoom);
//            tvRoomType = itemView.findViewById(R.id.tvRoomType);
//        }
//    }
//
//
//}
