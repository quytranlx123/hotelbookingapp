package com.example.hotelbookingapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.helper.FragmentSwitcher;
import com.example.hotelbookingapp.viewmodel.UserViewModel;


public class SettingsFragment extends PreferenceFragmentCompat {
    private FragmentSwitcher fragmentSwitcher;
    private UserViewModel userViewModel;
    private Preference loginPreference;
    private Preference logoutPreference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Đảm bảo rằng activity implement FragmentSwitcher
        if (requireActivity() instanceof FragmentSwitcher) {
            fragmentSwitcher = (FragmentSwitcher) requireActivity();
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        loginPreference = findPreference("login");
        logoutPreference = findPreference("logout");

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // Xử lý sự kiện đăng nhập
        if (loginPreference != null) {
            loginPreference.setOnPreferenceClickListener(preference -> {
                if (fragmentSwitcher != null) {
                    fragmentSwitcher.switchFragment(new LoginFragment());
                }
                return true;
            });
        }

        // Xử lý sự kiện đăng xuất
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(preference -> {
                userViewModel.logout();
                return true;
            });
        }

        // Theo dõi trạng thái đăng nhập
        userViewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                loginPreference.setVisible(false);
                logoutPreference.setVisible(true);
            } else {
                loginPreference.setVisible(true);
                logoutPreference.setVisible(false);
            }
        });
    }
}