package com.example.hotelbookingapp.ui.fragment.management;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.hotelbookingapp.R;

/**
 * A simple {@link Fragment} subclass for managing room types.
 */
public class RoomTypeManagementFragment extends Fragment {

    public RoomTypeManagementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xử lý bất kỳ logic cần thiết khi fragment được tạo
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_room_type_management, container, false);

        // Thực hiện các thao tác với giao diện (UI) ở đây
        // Ví dụ: tìm kiếm view, xử lý sự kiện click button, v.v.

        return rootView;
    }
}
