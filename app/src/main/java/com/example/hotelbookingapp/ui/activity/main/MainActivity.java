package com.example.hotelbookingapp.ui.activity.main;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.databinding.ActivityMainBinding;
import com.example.hotelbookingapp.ui.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

//public class MainActivity extends AppCompatActivity implements FragmentSwitcher {

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences sharedPreferences = getSharedPreferences("user_role", Context.MODE_PRIVATE);



        boolean isDarkMode = prefs.getBoolean("dark_mode", false);

        // Áp dụng trước khi setContentView
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setBackground(null);

        toolbar = findViewById(R.id.toolbar);

//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();

        setSupportActionBar(toolbar);
        animateFab();

        binding.bottomNavigationView.post(() -> {
            navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (destination.getId() != R.id.searchFragment
                        && destination.getId() != R.id.bookingFragment
                        && destination.getId() != R.id.favoriteFragment
                        && destination.getId() != R.id.settingsFragment) {
                    binding.bottomAppBar.setVisibility(View.GONE);
                    binding.fab.setVisibility(View.GONE);
                    binding.toolbar.setVisibility(View.GONE);
                } else {
                    binding.bottomAppBar.setVisibility(View.VISIBLE);
                    binding.fab.setVisibility(View.VISIBLE);
                    binding.toolbar.setVisibility(View.VISIBLE);
                }
            });
        });


// Chuyển đến CameraFragment khi nhấn FAB
        binding.fab.setOnClickListener(v -> {
            // Lấy vị trí của FAB trên màn hình
            int[] fabPosition = new int[2];
            v.getLocationInWindow(fabPosition);

            // Tạo Bundle để gửi dữ liệu qua CameraFragment
            Bundle args = new Bundle();
            args.putInt("fab_x", fabPosition[0] + v.getWidth() / 2);  // Lấy vị trí X của FAB
            args.putInt("fab_y", fabPosition[1] + v.getHeight() / 2);  // Lấy vị trí Y của FAB
            args.putInt("fab_radius", v.getWidth() / 2);  // Lấy bán kính của FAB

            // Chuyển hướng đến CameraFragment và truyền dữ liệu qua Bundle
            navController.navigate(R.id.cameraFragment, args);
        });
    }

//    public void showMainUI(boolean show) {
//        if (show) {
//            binding.bottomNavigationView.setVisibility(View.VISIBLE);
//        } else {
//            binding.bottomNavigationView.setVisibility(View.GONE);
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Toast.makeText(this, "Tìm kiếm", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_notify) {
            Toast.makeText(this, "Thông báo", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void animateFab() {
        ValueAnimator colorAnimator = ValueAnimator.ofArgb(
                getResources().getColor(R.color.lavender),
                getResources().getColor(R.color.black)
        );
        colorAnimator.setDuration(2000); // Thời gian thay đổi màu sắc
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE); // Lặp lại vô hạn
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE); // Đảo ngược màu sắc sau mỗi lần lặp

        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                binding.fab.setImageTintList(ColorStateList.valueOf((int) animator.getAnimatedValue()));
            }
        });

        colorAnimator.start();
    }
}
