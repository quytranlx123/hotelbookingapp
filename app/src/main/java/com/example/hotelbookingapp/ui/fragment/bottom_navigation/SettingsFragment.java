package com.example.hotelbookingapp.ui.fragment.bottom_navigation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.LiveData;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.databinding.FragmentSettingsBinding;
import com.example.hotelbookingapp.ui.viewmodel.UserViewModel;
//import com.example.hotelbookingapp.ui.viewmodel.UserViewModelFactory;

import java.util.Objects;

public class SettingsFragment extends Fragment {
    private UserViewModel userViewModel;
    private FragmentSettingsBinding binding;
    private NavController navController;
    private ScrollView scrollView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        scrollView = binding.scrollView;
        binding.adminSection.setVisibility(View.GONE);


//// Lấy SharedPreferences
//        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_role", Context.MODE_PRIVATE);
//
//        // Tạo UserViewModelFactory, truyền SharedPreferences vào
//        UserViewModelFactory factory = new UserViewModelFactory(sharedPreferences);
//
//        // Lấy ViewModel thông qua Factory
//        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);

        // Bây giờ bạn có thể dùng userViewModel
//        LiveData<String> userRoleLiveData = userViewModel.getUserRole();
//        userRoleLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String role) {
//                // Xử lý userRole.
//                System.out.println("User Role: " + role);
//            }
//        });

        // Khởi tạo ViewModel đơn giản, không dùng Factory
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Observer userRole
        userViewModel.getUserRole().observe(getViewLifecycleOwner(), role -> {
            System.out.println("User Role: " + role);
            if ("admin".equals(role)) {
                binding.adminSection.setVisibility(View.VISIBLE);
            } else {
                binding.adminSection.setVisibility(View.GONE);
            }
        });

        binding.loginLogoutButton.setOnClickListener(v ->
                navController.navigate(R.id.action_settingsFragment_to_loginFragment)
        );

        userViewModel.getUserRole().observe(getViewLifecycleOwner(), role -> {
            if ("admin".equals(role)) {
                binding.adminSection.setVisibility(View.VISIBLE);
            } else {
                binding.adminSection.setVisibility(View.GONE);
            }
        });

        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser != null) {
                // Người dùng đã đăng nhập
                binding.loginLogoutButton.setText("Đăng xuất");
                binding.loginLogoutButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
                binding.loginLogoutButton.setOnClickListener(v -> {
                    // Đăng xuất người dùng
                    userViewModel.logout();
                    binding.loginLogoutButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                });
            } else {
                // Người dùng chưa đăng nhập
                binding.loginLogoutButton.setText("Đăng nhập");
                binding.loginLogoutButton.setOnClickListener(v -> {
                    // Điều hướng sang trang loginFragment
                    navController.navigate(R.id.action_settingsFragment_to_loginFragment);
                });
            }
        });

        binding.dashboard.setOnClickListener(v -> {
            navController.navigate(R.id.action_settingsFragment_to_dashboardFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Lưu vị trí cuộn của ScrollView
        int scrollPosition = scrollView.getScrollY();
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("scroll_position", scrollPosition);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Khôi phục lại vị trí cuộn
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int savedScrollPosition = preferences.getInt("scroll_position", 0); // Mặc định là 0 nếu không tìm thấy
        scrollView.scrollTo(0, savedScrollPosition);

    }
}