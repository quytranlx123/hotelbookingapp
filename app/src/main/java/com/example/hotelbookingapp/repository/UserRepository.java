package com.example.hotelbookingapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserRepository {
    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<FirebaseUser> currentUser = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public UserRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(auth -> currentUser.setValue(auth.getCurrentUser()));
    }


    public MutableLiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        currentUser.setValue(firebaseAuth.getCurrentUser());
                    } else {
                        errorMessage.setValue("Đăng nhập thất bại: " + task.getException().getMessage());
                    }
                });
    }

    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        currentUser.setValue(firebaseAuth.getCurrentUser());
                    } else {
                        errorMessage.setValue("Đăng ký thất bại: " + task.getException().getMessage());
                    }
                });
    }

    public void logout() {
        firebaseAuth.signOut();
        currentUser.setValue(null);
    }
}
