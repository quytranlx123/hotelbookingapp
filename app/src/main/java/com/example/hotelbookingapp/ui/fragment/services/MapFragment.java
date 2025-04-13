package com.example.hotelbookingapp.ui.fragment.services;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hotelbookingapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private LatLng selectedLatLng;
    private String selectedAddress;

    private OnLocationSelectedListener mListener;

    public void setOnLocationSelectedListener(OnLocationSelectedListener listener) {
        this.mListener = listener;
    }


    public MapFragment() {
        // Bắt buộc constructor rỗng
    }

    public interface OnLocationSelectedListener {
        void onLocationSelected(LatLng latLng, String address);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Lấy SupportMapFragment và khởi tạo
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        geocoder = new Geocoder(getContext(), Locale.getDefault());

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(latLng -> {
            selectedLatLng = latLng;
            mMap.clear();

            // Thêm marker
            mMap.addMarker(new MarkerOptions().position(latLng).title("Vị trí đã chọn"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

            // Sử dụng Geocoder để lấy địa chỉ
            try {
                List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    selectedAddress = addresses.get(0).getAddressLine(0);
                    Toast.makeText(getContext(), "Địa chỉ: " + selectedAddress, Toast.LENGTH_LONG).show();

                    // Gọi listener để trả lại kết quả
                    if (mListener != null) {
                        mListener.onLocationSelected(selectedLatLng, selectedAddress);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Hàm gọi chỉ đường đến vị trí đã chọn
    public void navigateToSelectedLocation() {
        if (selectedLatLng != null) {
            Uri uri = Uri.parse("google.navigation:q=" +
                    selectedLatLng.latitude + "," + selectedLatLng.longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Không tìm thấy ứng dụng Google Maps", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
