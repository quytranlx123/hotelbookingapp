package com.example.hotelbookingapp.ui.fragment.bottom_navigation;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hotelbookingapp.databinding.FragmentCameraBinding;
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraFragment extends Fragment {

    private int fabX, fabY, fabRadius;
    private PreviewView previewView;
    private FragmentCameraBinding binding;
    private ExecutorService cameraExecutor;
    private BarcodeScanner barcodeScanner;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ProcessCameraProvider cameraProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialFadeThrough());

        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        startCamera();
                    } else {
                        Toast.makeText(requireContext(), "Quyền Camera bị từ chối!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        previewView = binding.previewView;

        Bundle args = getArguments();
        if (args != null) {
            fabX = args.getInt("fab_x");
            fabY = args.getInt("fab_y") - getStatusBarHeight();  // Trừ status bar nếu có
            fabRadius = args.getInt("fab_radius");
        }

        previewView.setVisibility(View.INVISIBLE);  // Ẩn trước

        previewView.post(() -> revealPreviewFromFab(fabX, fabY));  // Animate từ vị trí FAB

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        } else {
            startCamera();
        }

        barcodeScanner = com.google.mlkit.vision.barcode.BarcodeScanning.getClient(
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                        .build()
        );

        cameraExecutor = Executors.newSingleThreadExecutor();

        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        concealPreviewToFab(fabX, fabY);
                    }
                }
        );
    }

    private void revealPreviewFromFab(int centerX, int centerY) {
        float finalRadius = (float) Math.hypot(previewView.getWidth(), previewView.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(previewView, centerX, centerY, fabRadius, finalRadius);
        previewView.setVisibility(View.VISIBLE);
        anim.setDuration(500);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
    }

    private void concealPreviewToFab(int centerX, int centerY) {
        // Tính toán bán kính ban đầu để tạo hiệu ứng vòng tròn
        float initialRadius = (float) Math.hypot(previewView.getWidth(), previewView.getHeight());

        // Tạo hiệu ứng thu nhỏ (circular reveal)
        Animator anim = ViewAnimationUtils.createCircularReveal(previewView, centerX, centerY, initialRadius, fabRadius);
        anim.setDuration(500);
        anim.setInterpolator(new DecelerateInterpolator());

        // Khi hiệu ứng thu nhỏ kết thúc
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Ẩn PreviewView và giải phóng camera
                previewView.setVisibility(View.INVISIBLE);
                unbindCamera();
                // Quay lại màn hình trước đó
                NavHostFragment.findNavController(CameraFragment.this).navigateUp();
            }
        });
        anim.start();
    }


    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, this::processImage);
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                Camera camera = cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageAnalysis);

            } catch (Exception e) {
                Log.e("CameraX", "Lỗi khởi tạo camera", e);
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void unbindCamera() {
        if (cameraProvider != null) {
            cameraProvider.unbindAll();
        }
    }

    private void processImage(ImageProxy image) {
        @SuppressWarnings("UnsafeOptInUsageError")
        InputImage inputImage = InputImage.fromMediaImage(image.getImage(), image.getImageInfo().getRotationDegrees());

        barcodeScanner.process(inputImage)
                .addOnSuccessListener(barcodes -> {
                    for (Barcode barcode : barcodes) {
                        String qrText = barcode.getRawValue();
                        if (qrText != null && !qrText.isEmpty()) {
                            showToast(qrText);
                        }
                    }
                })
                .addOnFailureListener(e -> Log.e("CameraFragment", "QR Scan failed", e))
                .addOnCompleteListener(task -> image.close());
    }

    private void showToast(String message) {
        requireActivity().runOnUiThread(() ->
                Toast.makeText(requireContext(), "QR Code: " + message, Toast.LENGTH_SHORT).show()
        );
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbindCamera();
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
        }
        binding = null;
    }
}
