package com.example.chatties.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatties.R;
import com.example.chatties.databinding.ActivityVerifyResetPassBinding;

public class VerifyResetPassActivity extends AppCompatActivity {
    ActivityVerifyResetPassBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyResetPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnBacktoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        binding.btnBacktoReset.setOnClickListener(v -> {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            startActivity(intent);
            finish();
        });
    }
}