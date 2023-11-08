package com.example.chatties.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.chatties.Contract.ILoginContract;
import com.example.chatties.Entity.UserTable;
import com.example.chatties.Presenter.LoginActivityPresenter;
import com.example.chatties.R;
import com.example.chatties.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements ILoginContract.View {
    ActivityLoginBinding binding;
    LoginActivityPresenter presenter;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new LoginActivityPresenter(this);

        final Drawable hidepass = ContextCompat.getDrawable(this,R.drawable.icon_hidepass);
        final Drawable showpass = ContextCompat.getDrawable(this,R.drawable.icon_showpass);

        ShowHidePass(hidepass,showpass);

        binding.btnLogin.setOnClickListener(v -> {
            showLoading(true);
            if(ValidateInfo()){
                String email = binding.tbEmail.getText().toString();
                String password = binding.tbPassword.getText().toString();
                presenter.LoginClick(email,password);
            }

            //showLoading(false);
        });
        binding.btnForgetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            startActivity(intent);
        });
        binding.btnCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

    }
    private void ShowHidePass(Drawable hidepass, Drawable showpass){
        if (hidepass != null && showpass != null) {
            hidepass.setBounds(0, 0, hidepass.getIntrinsicWidth(), hidepass.getIntrinsicHeight());
            showpass.setBounds(0, 0, showpass.getIntrinsicWidth(), showpass.getIntrinsicHeight());

            binding.tbPassword.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (binding.tbPassword.getRight() - binding.tbPassword.getCompoundDrawables()[2].getBounds().width())) {
                            if (isPasswordVisible) {
                                isPasswordVisible = false;
                                binding.tbPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                binding.tbPassword.setCompoundDrawablesRelative(null, null, hidepass, null);
                            } else {
                                isPasswordVisible = true;
                                binding.tbPassword.setTransformationMethod(null);
                                binding.tbPassword.setCompoundDrawablesRelative(null, null, showpass, null);
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    private void showLoading(boolean loading) {
        if (loading) {
            binding.btnLogin.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.VISIBLE);
        }
    }
    private boolean ValidateInfo(){
        String email = binding.tbEmail.getText().toString();
        String password = binding.tbPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            binding.tbEmail.setError("Không được để trống");
            return false;
        }
        else if(TextUtils.isEmpty(password)){
            binding.tbEmail.setError("Không được để trống");
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void FinishLogin(boolean isSuccess, Exception e, String id) {
        if(isSuccess){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            showLoading(false);
        }

    }
}