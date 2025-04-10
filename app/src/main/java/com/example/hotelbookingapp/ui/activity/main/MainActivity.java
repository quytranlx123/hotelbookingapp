package com.example.hotelbookingapp.ui.activity.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.databinding.ActivityMainBinding;
import com.example.hotelbookingapp.ui.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

//public class MainActivity extends AppCompatActivity implements FragmentSwitcher {

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    NavController navController;
    Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setBackground(null);
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();

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
                    || destination.getId() == R.id.hotelManagementFragment
                    || destination.getId() == R.id.roomFragment
                    || destination.getId() == R.id.registerFragment
                    || destination.getId() == R.id.dashboardFragment
                    || destination.getId() == R.id.cameraFragment) {
                binding.bottomAppBar.setVisibility(View.GONE);
                binding.fab.hide();
                binding.toolbar.setVisibility(View.GONE);
            } else {
                binding.bottomAppBar.setVisibility(View.VISIBLE);
                binding.fab.show();
                binding.toolbar.setVisibility(View.VISIBLE);
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
}
