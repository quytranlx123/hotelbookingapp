package com.example.hotelbookingapp.ui.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final Observer<String> roleObserver;

    public UserViewModel() {
        userRepository = new UserRepository();

        // Khởi tạo Observer để nghe role, tránh leak observeForever
        roleObserver = role -> {
            if (role != null) {
                loginSuccess.setValue(true);
            } else {
                loginSuccess.setValue(false);
            }
        };
        userRepository.getUserRole().observeForever(roleObserver);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        userRepository.getUserRole().removeObserver(roleObserver);  // tránh leak
    }

    public MutableLiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public MutableLiveData<String> getErrorMessage() {
        return userRepository.getErrorMessage();
    }

    public MutableLiveData<String> getUserRole() {
        return userRepository.getUserRole();
    }

    public MutableLiveData<Boolean> getLoginSuccess() {
        return userRepository.getLoginSuccess();  // dùng LiveData từ Repository
    }

    public void login(String email, String password, Context context) {
        userRepository.login(email, password);

        userRepository.getUserRole().observeForever(role -> {
            if (role != null) {
                SharedPreferences prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
                prefs.edit()
                        .putBoolean("is_logged_in", true)
                        .putBoolean("is_admin", "admin".equals(role))
                        .apply();
            }
        });
    }

    public void register(String email, String password) {
        userRepository.register(email, password);
    }

    public void logout(@NonNull Context context) {
        userRepository.logout();

        SharedPreferences prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        prefs.edit()
                .putBoolean("is_logged_in", false)
                .putBoolean("is_admin", false)
                .apply();

        loginSuccess.setValue(false);
    }

    public void saveLoginState(Context context, boolean isLoggedIn) {
        SharedPreferences prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply();
    }
}

