package com.example.hotelbookingapp.ui.fragment.bottom_navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.ui.activity.main.MainActivity;
import com.example.hotelbookingapp.ui.viewmodel.UserViewModel;

public class SettingsFragment extends PreferenceFragmentCompat {

    private UserViewModel userViewModel;
    private Preference loginPreference;
    private Preference logoutPreference;
    private Preference darkModePreference;
    private Preference dashboardPreference;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        navController = NavHostFragment.findNavController(this);

        loginPreference = findPreference("login");
        logoutPreference = findPreference("logout");
        dashboardPreference = findPreference("dashboard");
        darkModePreference = findPreference("dark_mode");

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        updatePreferenceVisibility();  // cập nhật UI theo trạng thái đăng nhập & role

        if (dashboardPreference != null) {
            dashboardPreference.setOnPreferenceClickListener(preference -> {
                navController.navigate(R.id.dashboardFragment);
                return true;
            });
        }

        if (darkModePreference != null) {
            darkModePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isDarkMode = (boolean) newValue;
                ((MainActivity) requireActivity()).setDarkMode(isDarkMode);
                return true;
            });
        }

        if (loginPreference != null) {
            loginPreference.setOnPreferenceClickListener(preference -> {
                navController.navigate(R.id.loginFragment);
                return true;
            });
        }

        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(preference -> {
                userViewModel.logout(requireContext());  // logout + xoá trạng thái

                // Hiện nút Login, ẩn Logout
                if (loginPreference != null) loginPreference.setVisible(true);
                if (logoutPreference != null) logoutPreference.setVisible(false);
                if (dashboardPreference != null) dashboardPreference.setVisible(false);

                return true;
            });
        }
    }

    private void updatePreferenceVisibility() {
        SharedPreferences prefs = requireContext().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);
        boolean isAdmin = prefs.getBoolean("is_admin", false);

        if (loginPreference != null) loginPreference.setVisible(!isLoggedIn);
        if (logoutPreference != null) logoutPreference.setVisible(isLoggedIn);

        if (dashboardPreference != null) {
            dashboardPreference.setVisible(isLoggedIn && isAdmin);  // chỉ hiện nếu là admin
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) requireActivity()).showMainUI(true);
    }
}
