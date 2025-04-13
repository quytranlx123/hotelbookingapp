package com.example.hotelbookingapp.ui.fragment.login;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.hotelbookingapp.ui.viewmodel.UserViewModel;

public class LoginFragment extends Fragment {
    NavController navController;

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

        navController = Navigation.findNavController(view);
        Button btnLogin = view.findViewById(R.id.loginButton);
        EditText editTextEmail = view.findViewById(R.id.username);
        EditText editTextPassword = view.findViewById(R.id.password);
        TextView signupText = view.findViewById(R.id.signupText);

        // Gắn sự kiện click login
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

            // Gọi ViewModel login
            userViewModel.login(email, password, requireContext());
        });

        // Quan sát trạng thái login thành công
        userViewModel.getLoginSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {
                Toast.makeText(requireContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                userViewModel.saveLoginState(requireContext(), true);
                navController.navigate(R.id.searchFragment);  // <-- chuyển hướng sau khi login thành công
            }
        });

        // Quan sát lỗi
        userViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null) {
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        // Nếu có user từ trước
        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser != null) {
                userViewModel.saveLoginState(requireContext(), true);
                // Có thể chuyển hướng sang searchFragment nếu muốn, nhưng thông thường để loginSuccess điều khiển là đủ.
            }
        });

        // Điều hướng sang register khi nhấn nút
        signupText.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_registerFragment));
    }
}
