//package com.example.hotelbookingapp.ui.adapter;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.hotelbookingapp.R;
//
//public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.RoomTypeViewHolders>{
//    private String roomType;
//    private String roomTypeDetail;
//    @NonNull
//    @Override
//    public RoomTypeViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = getLayoutInflater().inflate(R.layout.item_room_type,parent,false);
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RoomTypeViewHolders holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public class RoomTypeViewHolders extends RecyclerView.ViewHolder {
//        private TextView roomType;
//        private RecyclerView rcvRoomType;
//
//        public RoomTypeViewHolders(@NonNull View itemView) {
//            super(itemView);
//            roomType = itemView.findViewById(R.id.tvRoomType);
//            rcvRoomType = itemView.findViewById(R.id.rcvRoomType);
//        }
//    }
//
//}
