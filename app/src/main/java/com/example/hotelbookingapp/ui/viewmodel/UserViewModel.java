package com.example.hotelbookingapp.ui.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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

        // Đăng ký observer và gỡ bỏ đúng cách
        userRepository.getUserRole().observeForever(roleObserver);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Gỡ bỏ observer để tránh memory leak
        userRepository.getUserRole().removeObserver(roleObserver);
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
        return loginSuccess;  // trả về loginSuccess trong ViewModel
    }

    // Đăng nhập
    public void login(String email, String password, Context context) {
        userRepository.login(email, password);

        // Đăng ký observer cho userRole
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

    // Đăng ký
    public void register(String email, String password) {
        userRepository.register(email, password);
    }

    // Đăng xuất
    public void logout(@NonNull Context context) {
        userRepository.logout();

        // Cập nhật lại trạng thái đăng nhập trong SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        prefs.edit()
                .putBoolean("is_logged_in", false)
                .putBoolean("is_admin", false)
                .apply();

        // Đặt lại trạng thái loginSuccess sau khi đăng xuất
        loginSuccess.setValue(false);
    }

    // Lưu trạng thái đăng nhập
    public void saveLoginState(Context context, boolean isLoggedIn) {
        SharedPreferences prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply();
    }
}
