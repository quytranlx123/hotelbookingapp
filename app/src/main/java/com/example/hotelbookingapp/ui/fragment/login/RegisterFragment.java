package com.example.hotelbookingapp.ui.fragment.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.callback.RegisterCallback;
import com.example.hotelbookingapp.ui.viewmodel.UserViewModel;

public class RegisterFragment extends Fragment {

    private EditText etName, etEmail, etPassword;
    private UserViewModel userViewModel;
    private NavController navController;
    private Button btnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Khởi tạo view
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnRegister = view.findViewById(R.id.btnRegister);

        // Khởi tạo ViewModel Firebase
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        return view;
    }

    private void register() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

//        userViewModel.register(email, password, new RegisterCallback() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
//                clearFields(); // 💡 chỉ clear khi thành công
//            }
//
//            @Override
//            public void onFailure(String message) {
//                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        TextView signInText = view.findViewById(R.id.signInText);

        signInText.setOnClickListener(v -> {
            if (navController.getCurrentDestination() != null &&
                    navController.getCurrentDestination().getId() == R.id.registerFragment) {
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

//        userViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
//            if (errorMsg != null) {
//                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
//            }
//        });

        btnRegister.setOnClickListener(v -> {
            register();
            clearFields();
        });
    }

    private void clearFields() {
        if (etEmail != null) etEmail.setText("");
        if (etPassword != null) etPassword.setText("");
    }
}


