package com.example.hotelbookingapp.data.repository;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hotelbookingapp.data.callback.RegisterCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserRepository {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final MutableLiveData<FirebaseUser> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> userRole = new MutableLiveData<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

//    private final SharedPreferences sharedPreferences;

//    public UserRepository(SharedPreferences sharedPreferences) {
//        this.sharedPreferences = sharedPreferences;
//        firebaseAuth.addAuthStateListener(auth -> {
//            userLiveData.setValue(auth.getCurrentUser());  // Thêm dấu đóng )
//            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//            if (currentUser != null) {
//                loadUserRole(currentUser.getUid());
//            } else {
//                userRole.setValue(null);
//            }
//        });
//    }

    public UserRepository() {
        firebaseAuth.addAuthStateListener(auth -> {
            FirebaseUser currentUser = auth.getCurrentUser();
            userLiveData.setValue(currentUser);
            if (currentUser != null) {
                loadUserRole(currentUser.getUid());
            } else {
                userRole.setValue(null);
            }
        });
    }


    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<String> getUserRole() {
        return userRole;
    }


    // Đăng nhập
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                userLiveData.setValue(user);
                if (user != null) {
                    loadUserRole(user.getUid());
                }
            } else {
                userLiveData.setValue(null);
                errorLiveData.setValue("Đăng nhập thất bại: " + task.getException().getMessage());
            }
        });
    }

    // Lấy Role từ Firestore và lưu SharedPreferences
    private void loadUserRole(String uid) {
        db.collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");
                        userRole.setValue(role);
//                        // Lưu vào SharedPreferences
//                        sharedPreferences.edit().putString("user_role", role).apply();
                    } else {
                        userRole.setValue(null);
                        errorLiveData.setValue("Không tìm thấy thông tin role.");
                    }
                })
                .addOnFailureListener(e -> {
                    userRole.setValue(null);
                    errorLiveData.setValue("Lỗi khi lấy role: " + e.getMessage());
                });
    }

    //    // Đăng ký
//    public void register(String email, String password, RegisterCallback callback) {
//        firebaseAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        FirebaseUser user = firebaseAuth.getCurrentUser();
//                        currentUser.setValue(user);
//
//                        if (user != null) {
//                            // Set role mặc định cho user mới
//                            db.collection("users").document(user.getUid())
//                                    .set(new HashMap<String, Object>() {{
//                                        put("email", user.getEmail());
//                                        put("role", "customer");  // Hoặc 'admin'
//                                    }})
//                                    .addOnSuccessListener(aVoid -> {
//                                        userRole.setValue("customer");
//                                        callback.onSuccess();
//                                    }) // Cập nhật role
//                                    .addOnFailureListener(e -> {
//                                        errorMessage.setValue("Lỗi khi lưu role: " + e.getMessage());
//                                        callback.onFailure("Lỗi khi lưu role: " + e.getMessage());
//                                    });
//                        }
//                    } else {
//                        String error = "Đăng ký thất bại: " + task.getException().getMessage();
//                        errorMessage.setValue(error);
//                        callback.onFailure(error);
//                    }
//                });
//    }
    // Đăng xuất
    public void logout() {
        firebaseAuth.signOut();
        userLiveData.setValue(null);
        userRole.setValue(null);
//        sharedPreferences.edit().remove("user_role").apply();
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
}
