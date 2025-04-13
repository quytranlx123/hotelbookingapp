package com.example.hotelbookingapp.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserRepository {
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore db;
    private final MutableLiveData<FirebaseUser> currentUser = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<String> userRole = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();

    public UserRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        firebaseAuth.addAuthStateListener(auth -> currentUser.setValue(auth.getCurrentUser()));
    }

    public MutableLiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    public MutableLiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getUserRole() {
        return userRole;
    }

    // Đăng nhập
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                currentUser.setValue(user);

                if (user != null) {
                    db.collection("users").document(user.getUid()).get()
                            .addOnSuccessListener(doc -> {
                                if (doc.exists()) {
                                    String role = doc.getString("role");
                                    userRole.setValue(role); // Cập nhật role người dùng
                                    loginSuccess.setValue(true);
                                } else {
                                    errorMessage.setValue("Không tìm thấy dữ liệu người dùng.");
                                    loginSuccess.setValue(false);
                                }
                            })
                            .addOnFailureListener(e -> errorMessage.setValue("Không thể lấy dữ liệu người dùng: " + e.getMessage()));
                }
            } else {
                errorMessage.setValue("Đăng nhập thất bại: " + task.getException().getMessage());
            }
        });
    }

    // Đăng ký
    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        currentUser.setValue(user);

                        if (user != null) {
                            // Set role mặc định cho user mới
                            db.collection("users").document(user.getUid())
                                    .set(new HashMap<String, Object>() {{
                                        put("email", user.getEmail());
                                        put("role", "user");  // Hoặc 'admin'
                                    }})
                                    .addOnSuccessListener(aVoid -> userRole.setValue("user")) // Cập nhật role
                                    .addOnFailureListener(e -> {
                                        errorMessage.setValue("Lỗi khi lưu role: " + e.getMessage());
                                    });
                        }
                    } else {
                        errorMessage.setValue("Đăng ký thất bại: " + task.getException().getMessage());
                    }
                });
    }

    // Đăng xuất
    public void logout() {
        firebaseAuth.signOut();
        currentUser.setValue(null);
        userRole.setValue(null);
    }
}
