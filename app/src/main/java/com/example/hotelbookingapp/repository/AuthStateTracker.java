package com.example.hotelbookingapp.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthStateTracker {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public void startAuthStateListener() {
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = firebaseAuth1 -> {
            FirebaseUser currentUser = firebaseAuth1.getCurrentUser();
            if (currentUser != null) {
                // Người dùng đã đăng nhập
                System.out.println("Người dùng đăng nhập: " + currentUser.getEmail());
            } else {
                // Người dùng đã đăng xuất
                System.out.println("Người dùng đã đăng xuất");
            }
        };

        // Thêm listener vào FirebaseAuth
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void stopAuthStateListener() {
        if (firebaseAuth != null && authStateListener != null) {
            // Gỡ bỏ listener khi không cần thiết
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
