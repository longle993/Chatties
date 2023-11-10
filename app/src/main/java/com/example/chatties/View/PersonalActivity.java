package com.example.chatties.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.chatties.Contract.IPersonalContract;
import com.example.chatties.Entity.User;
import com.example.chatties.Entity.UserTable;
import com.example.chatties.Presenter.PersonalActivityPresenter;
import com.example.chatties.R;
import com.example.chatties.databinding.ActivityPersonalBinding;

public class PersonalActivity extends BaseActivity implements IPersonalContract.View {
    ActivityPersonalBinding binding;
    PersonalActivityPresenter presenter;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getStringExtra(UserTable.USER_ID);
        presenter = new PersonalActivityPresenter(this);
        presenter.LoadingInfo(id);

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnAddfr.setOnClickListener(v -> {
            presenter.SendRequest(id);
        });

        binding.btnMessage.setOnClickListener(v -> {
            Intent intent = getIntent();
            if (intent != null && intent.getComponent() != null &&
                    ChatActivity.class.getName().equals(intent.getComponent().getClassName())) {
                finish();
            } else {
                Intent chatIntent = new Intent(this, ChatActivity.class);
                startActivity(chatIntent);
                finish();
            }
        });
    }

    @Override
    public void onLoadInfo(boolean isSuccess, Exception e, User user) {
        if(isSuccess){
            Glide.with(this).load(user.getAvatar()).into(binding.imvAvatar);
            binding.tvNameDisplay.setText(user.getName());
        }
        else {
            e.printStackTrace();
        }
        showLoading(false);
    }

    @Override
    public void onFinishSendRequest() {
        binding.btnAddfr.setVisibility(View.GONE);
        binding.btnRemoveAddfr.setVisibility(View.VISIBLE);
    }

    @Override
    public void checkRequest(boolean isSend) {
        if(isSend){
            binding.btnAddfr.setVisibility(View.GONE);
            binding.btnRemoveAddfr.setVisibility(View.VISIBLE);
        }
    }

    private void showLoading(boolean loading) {
        if (!loading) {
            binding.progressBar.setVisibility(View.GONE);
            binding.layoutInfo.setVisibility(View.VISIBLE);
        }
    }

}