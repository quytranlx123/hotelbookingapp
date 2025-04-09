package com.example.hotelbookingapp.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.hotelbookingapp.ui.MainActivity;
import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.ui.viewmodel.UserViewModel;


public class SettingsFragment extends PreferenceFragmentCompat {
    private UserViewModel userViewModel;
    private Preference loginPreference;
    private Preference logoutPreference;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        loginPreference = findPreference("login");
        logoutPreference = findPreference("logout");
        Preference dashboardPreference = findPreference("dashboard");
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);


        // Đọc trạng thái login từ SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);

        if (loginPreference != null) loginPreference.setVisible(!isLoggedIn);
        if (logoutPreference != null) logoutPreference.setVisible(isLoggedIn);

        //Sự kiện ấn nút quản lý cơ sở dữ liệu
        if (dashboardPreference != null) {
            dashboardPreference.setOnPreferenceClickListener(preference -> {
                navController.navigate(R.id.dashboardFragment);
                return true;
            });
        }

        //Sự kiện ấn nút đăng nhập
        if (loginPreference != null) {
            loginPreference.setOnPreferenceClickListener(preference -> {
                navController.navigate(R.id.loginFragment);
                return true;
            });
        }

        // Xử lý sự kiện đăng xuất
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(preference -> {
                userViewModel.logout();
                userViewModel.saveLoginState(requireContext(), false); // cập nhật trạng thái
                loginPreference.setVisible(true);
                logoutPreference.setVisible(false);
                return true;
            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) requireActivity()).showMainUI(true);
    }
}