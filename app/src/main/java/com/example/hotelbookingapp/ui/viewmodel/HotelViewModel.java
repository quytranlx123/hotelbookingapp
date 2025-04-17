package com.example.hotelbookingapp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.models.Hotel;
import com.example.hotelbookingapp.data.repository.HotelRepository;

import java.util.List;

public class HotelViewModel extends ViewModel {

    private final HotelRepository hotelRepository;
    private boolean isLoaded = false;

    public HotelViewModel() {
        hotelRepository = new HotelRepository();
    }

    private final MediatorLiveData<List<Hotel>> hotels = new MediatorLiveData<>();

    public LiveData<List<Hotel>> getHotels() {
        if (!isLoaded) {
            LiveData<List<Hotel>> source = hotelRepository.getHotels();
            hotels.addSource(source, data -> {
                hotels.setValue(data);
                hotels.removeSource(source); // Gỡ sau khi lấy xong
            });
            isLoaded = true;
        }
        return hotels;
    }
    private void loadHotelsFromFirebase() {
        hotelRepository.getHotels().observeForever(newHotels -> {
            hotels.setValue(newHotels);
        });
    }
}
