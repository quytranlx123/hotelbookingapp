package com.example.hotelbookingapp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();

    public UserViewModel(SavedStateHandle savedStateHandle) {
        userRepository = new UserRepository();
    }

    public LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public LiveData<String> getErrorMessage() {
        return userRepository.getErrorMessage();
    }

    public void login(String email, String password) {
        userRepository.login(email, password);
    }

    public void register(String email, String password) {
        userRepository.register(email, password);
    }

    public void logout() {
        userRepository.logout();
    }
}