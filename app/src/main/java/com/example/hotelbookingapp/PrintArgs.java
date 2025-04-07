package com.example.hotelbookingapp;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PrintArgs {

    private static final String TAG = "PrintLog";

    // 1. In ra Logcat
    public static void log(Object... args) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            output.append("Arg ").append(i + 1).append(": ").append(args[i]).append("\n");
        }
        Log.d(TAG, output.toString());
    }

    // 2. Hiển thị bằng Toast
    public static void toast(Context context, Object... args) {
        StringBuilder output = new StringBuilder();
        for (Object arg : args) {
            output.append(arg).append(" | ");
        }
        Toast.makeText(context, output.toString(), Toast.LENGTH_LONG).show();
    }

    // 3. Hiển thị thông tin bằng AlertDialog
    public static void showDialog(Context context, Object... args) {
        StringBuilder message = new StringBuilder();
        for (Object arg : args) {
            message.append(arg).append("\n");
        }

        new AlertDialog.Builder(context)
                .setTitle("Debug Info")
                .setMessage(message.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    // 4. Ghi ra file trong bộ nhớ máy
    public static void writeToFile(Context context, Object... args) {
        StringBuilder content = new StringBuilder();
        for (Object arg : args) {
            content.append(arg).append("\n");
        }

        try {
            File file = new File(context.getFilesDir(), "debug_log.txt");
            FileOutputStream fos = new FileOutputStream(file, true); // true = append
            fos.write(content.toString().getBytes());
            fos.close();
            Toast.makeText(context, "Đã ghi log vào file", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 5. Hiển thị thông tin trên TextView
    public static void showOnTextView(TextView tv, Object... args) {
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            builder.append(arg).append("\n");
        }
        tv.setText(builder.toString());
    }
}
