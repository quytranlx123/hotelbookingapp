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
    private NavController navController;

    private UserViewModel userViewModel;
    private Button btnLogin;
    private TextView signupText;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
        navController = Navigation.findNavController(view);
        btnLogin = view.findViewById(R.id.loginButton);
        editTextEmail = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);
        signupText = view.findViewById(R.id.signupText);
//
//        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//
//        // Quan sát trạng thái login thành công
        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Toast.makeText(requireContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_loginFragment_to_searchFragment);  // <-- chuyển hướng sau khi login thành công
            } else {
                Toast.makeText(requireContext(), "Người dùng chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            }
        });
//
//        // Gắn sự kiện click login
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
            userViewModel.login(email, password);
        });

//
//        // Quan sát lỗi
//        userViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
//            if (errorMsg != null) {
//                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Quan sát trạng thái người dùng đã đăng nhập
//        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), firebaseUser -> {
//            if (firebaseUser != null) {
//                userViewModel.saveLoginState(requireContext(), true);
//                // Điều hướng sang searchFragment nếu có người dùng đăng nhập từ trước
//                navController.navigate(R.id.searchFragment);
//            }
//        });
//
//
//        // Điều hướng sang register khi nhấn nút
//        signupText.setOnClickListener(v -> {
//            if (navController.getCurrentDestination() != null &&
//                    navController.getCurrentDestination().getId() != R.id.registerFragment) {
//                navController.navigate(R.id.action_loginFragment_to_registerFragment);
//            }
//        });
    }
}
