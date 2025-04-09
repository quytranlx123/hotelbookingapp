package com.example.hotelbookingapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.database.dao.UserDAO;
import com.example.hotelbookingapp.data.models.User;


public class RegisterFragment extends Fragment {

    private EditText etName, etEmail, etPassword;
    private UserDAO userDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        view.findViewById(R.id.btnRegister).setOnClickListener(v -> register());

        userDAO = new UserDAO(getContext());
        return view;
    }

    private void register() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userDAO.isEmailExists(email)) {
            Toast.makeText(getContext(), "Email đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = userDAO.registerUser(new User(name, email, password));
        if (result) {
            Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            etName.setText(""); etEmail.setText(""); etPassword.setText("");
        } else {
            Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
