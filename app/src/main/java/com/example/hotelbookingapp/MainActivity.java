package com.example.hotelbookingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.hotelbookingapp.databinding.ActivityMainBinding;
import com.example.hotelbookingapp.fragment.BookingFragment;
import com.example.hotelbookingapp.fragment.CameraFragment;
import com.example.hotelbookingapp.fragment.FavoriteFragment;
import com.example.hotelbookingapp.fragment.SearchFragment;
import com.example.hotelbookingapp.fragment.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//public class MainActivity extends AppCompatActivity implements FragmentSwitcher {

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.bottomNavigationView.setBackground(null);

//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();

        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        binding.bottomNavigationView.post(() -> {
            navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        });


        binding.fab.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CameraFragment()).addToBackStack(null).commit();
        });
    }
    public void showMainUI(boolean show) {
        if (show) {
            binding.bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            binding.bottomNavigationView.setVisibility(View.GONE);
        }
    }

}
