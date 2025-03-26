package com.example.hotelbookingapp.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.hotelbookingapp.MainActivity;
import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.databinding.ActivityMainBinding;
import com.example.hotelbookingapp.viewmodel.UserViewModel;


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

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) requireActivity()).showMainUI(true); // Hiện lại UI khi thoát Login
    }
}