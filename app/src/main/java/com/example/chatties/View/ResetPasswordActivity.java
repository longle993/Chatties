package com.example.chatties.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.chatties.Contract.IResetPassContract;
import com.example.chatties.Presenter.ResetPasswordActivityPresenter;
import com.example.chatties.R;
import com.example.chatties.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends AppCompatActivity implements IResetPassContract.View {
    ActivityResetPasswordBinding binding;
    ResetPasswordActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        presenter = new ResetPasswordActivityPresenter(this);

        binding.btnResetPass.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            }
            else{
                presenter.SendEmailReset(binding.edtEmail.getText().toString());
            }
        });
        setContentView(binding.getRoot());
    }

    @Override
    public void onFinishSending(boolean isSuccess, Exception e) {
        if(isSuccess){
            Intent intent = new Intent(this, VerifyResetPassActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}