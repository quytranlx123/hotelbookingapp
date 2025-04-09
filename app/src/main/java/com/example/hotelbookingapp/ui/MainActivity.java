package com.example.hotelbookingapp.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.databinding.ActivityMainBinding;
import com.example.hotelbookingapp.ui.fragment.CameraFragment;
import com.example.hotelbookingapp.ui.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

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
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();

        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        binding.bottomNavigationView.post(() -> {
            navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        });


        binding.fab.setOnClickListener(v -> {
            navController.navigate(R.id.cameraFragment);
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.loginFragment
                    || destination.getId() == R.id.roomFragment
                    || destination.getId() == R.id.registerFragment
                    || destination.getId() == R.id.dashboardFragment
                    || destination.getId() == R.id.cameraFragment) {
                binding.bottomAppBar.setVisibility(View.GONE);
                binding.fab.hide();
            } else {
                binding.bottomAppBar.setVisibility(View.VISIBLE);
                binding.fab.show();
            }
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
