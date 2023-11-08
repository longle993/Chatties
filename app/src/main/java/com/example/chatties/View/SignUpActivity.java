package com.example.chatties.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.chatties.Contract.ISignUpContract;
import com.example.chatties.Entity.User;
import com.example.chatties.Presenter.SignUpActivityPresenter;
import com.example.chatties.R;
import com.example.chatties.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity implements ISignUpContract.View {
    ActivitySignUpBinding binding;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    Uri imageSource;
    SignUpActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new SignUpActivityPresenter(this);

        SetLauncher();
        binding.imvAvatar.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            imagePickerLauncher.launch(Intent.createChooser(intent, "Chọn ảnh từ thư viện"));
        });

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnSignUp.setOnClickListener(v -> {
            if(ValidateInfo()){
                User user = new User();
                user.setName(binding.edtUsername.getText().toString());
                user.setEmail(binding.edtEmail.getText().toString());
                user.setPassword(binding.edtPassword.getText().toString());

                presenter.onSignUpClicked(user,imageSource);
            }
        });

    }

    private boolean ValidateInfo(){
        String username = binding.edtUsername.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String password = binding.edtPassword.getText().toString();
        String confirmPass = binding.edtCfpassword.getText().toString();

        if(TextUtils.isEmpty(username)){
            binding.edtUsername.setError("Không hợp lệ");
            return false;
        }
        if(!email.matches(emailPattern)){
            binding.edtEmail.setError("Không hợp lệ");
            return false;
        }
        if(password.length()<6){
            binding.edtPassword.setError("Phải có 6 ký tự trở lên");
            return false;
        }
        if(!password.equals(confirmPass)){
            binding.edtCfpassword.setError("Mật khẩu không trùng khớp");
        }
        return true;
    }
    private void SetLauncher(){
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == SignUpActivity.RESULT_OK) {
                        imageSource = result.getData().getData();
                        binding.imvAvatar.setImageURI(imageSource);
                    }
                });
    }

    @Override
    public void onFinish(boolean isSuccess, Exception e) {
        if(isSuccess){
            Toast.makeText(this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, VerifyAccountActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}