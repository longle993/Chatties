package com.example.chatties.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatties.databinding.ActivityVerifyAccountBinding;

public class VerifyAccountActivity extends AppCompatActivity {
    ActivityVerifyAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBacktoSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
        binding.btnBacktoLogin.setOnClickListener(v -> {
            finish();
        });
    }
}