//package com.example.hotelbookingapp.ui.viewmodel;
//
//import android.content.SharedPreferences;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//public class UserViewModelFactory implements ViewModelProvider.Factory {
//
//    private final SharedPreferences sharedPreferences;
//
//    public UserViewModelFactory(SharedPreferences sharedPreferences) {
//        this.sharedPreferences = sharedPreferences;
//    }
//
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(UserViewModel.class)) {
//            return (T) new UserViewModel(sharedPreferences);
//        }
//        throw new IllegalArgumentException("Unknown ViewModel class");
//    }
//}