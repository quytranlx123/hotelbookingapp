package com.example.hotelbookingapp.ui.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.callback.RegisterCallback;
import com.example.hotelbookingapp.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends ViewModel {
    private final UserRepository userRepository;

    //    public UserViewModel(SharedPreferences sharedPreferences) {
//        userRepository = new UserRepository(sharedPreferences);
//    }
    public UserViewModel() {
        userRepository = new UserRepository();
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userRepository.getUserLiveData();
    }

    public LiveData<String> getErrorLiveData() {
        return userRepository.getErrorLiveData();
    }

    public LiveData<String> getUserRole() {
        return userRepository.getUserRole();
    }

    public void login(String email, String password) {
        userRepository.login(email, password);
    }

    public void logout() {
        userRepository.logout();
    }

}
