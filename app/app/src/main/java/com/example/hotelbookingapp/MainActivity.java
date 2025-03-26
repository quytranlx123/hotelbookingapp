package com.example.hotelbookingapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hotelbookingapp.databinding.ActivityMainBinding;
import com.example.hotelbookingapp.fragment.BookingFragment;
import com.example.hotelbookingapp.fragment.CameraFragment;
import com.example.hotelbookingapp.fragment.FavoriteFragment;
import com.example.hotelbookingapp.fragment.SearchFragment;
import com.example.hotelbookingapp.fragment.SettingsFragment;
import com.example.hotelbookingapp.helper.FragmentSwitcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements FragmentSwitcher {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        setContentView(binding.getRoot());

        if (currentUser == null) {
            Toast.makeText(MainActivity.this, "No User", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Have User", Toast.LENGTH_SHORT).show();
        }

        switchFragment(new SearchFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.favorite) {
                selectedFragment = new FavoriteFragment();
            } else if (itemId == R.id.settings) {
                selectedFragment = new SettingsFragment();
            } else if (itemId == R.id.booking) {
                selectedFragment = new BookingFragment();
            }


            if (selectedFragment != null) {
                switchFragment(selectedFragment);
            }

            return true;
        });

        binding.fab.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CameraFragment()).addToBackStack(null).commit();
        });
    }

    @Override
    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
}