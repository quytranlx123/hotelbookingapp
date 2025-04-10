package com.example.hotelbookingapp;

import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "djv2mqfj0");
        config.put("api_key", "746363583665152");
        config.put("api_secret", "-w8kwmeseGax34Iu_dLy7QDqhgg");

        MediaManager.init(this, config);
    }
}
