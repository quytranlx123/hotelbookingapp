package com.example.hotelbookingapp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.viewmodel.UserViewModel;

public class LoginFragment extends Fragment {

    private UserViewModel userViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnLogin = view.findViewById(R.id.loginButton);
        EditText editTextEmail = view.findViewById(R.id.username);
        EditText editTextPassword = view.findViewById(R.id.password);

        btnLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                editTextEmail.setError("Vui lòng nhập email!");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                editTextPassword.setError("Vui lòng nhập mật khẩu!");
                return;
            }

            userViewModel.login(email, password);

        });

        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser != null) {
                Toast.makeText(requireContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        // Lắng nghe lỗi đăng nhập
        userViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null) {
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
