package com.example.chatties.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.chatties.Contract.ISettingContract;
import com.example.chatties.Entity.User;
import com.example.chatties.Entity.UserTable;
import com.example.chatties.Presenter.SettingFragmentPresenter;
import com.example.chatties.View.LoginActivity;
import com.example.chatties.databinding.FragmentChatsBinding;
import com.example.chatties.databinding.FragmentSettingBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Fragment_Setting extends Fragment implements ISettingContract.View {
    FragmentSettingBinding binding;
    private FragmentManager manager;
    SettingFragmentPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater,container,false);
        manager =getActivity().getSupportFragmentManager();
        presenter = new SettingFragmentPresenter(this);
        presenter.LoadUserInfo(FirebaseAuth.getInstance().getUid());

        binding.btnLogout.setOnClickListener(v -> {
            presenter.Logout();
        });

        return  binding.getRoot();
    }

    @Override
    public void onLoadInfo(boolean isSuccess, Exception e, User user) {
        if(isSuccess){
            binding.tvEmail.setText(user.getEmail());
            binding.tvName.setText(user.getName());
            if(TextUtils.isEmpty(user.getBirthday())){
                binding.tvBirthday.setText("Chưa thiết lập");
            }
            binding.tvNameDisplay.setText(user.getName());
            Glide.with(this).load(user.getAvatar()).into(binding.imvAvatar);
            showLoading(false);
        }
    }

    @Override
    public void onLogout(boolean isSuccess, Exception e) {
        if(isSuccess){
            Intent intent = new Intent(getActivity(), LoginActivity.class); // Thay thế LoginActivity bằng tên Activity đăng nhập của bạn
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void showLoading(boolean loading) {
        if (!loading) {
            binding.progressBar.setVisibility(View.GONE);
            binding.layoutInfo.setVisibility(View.VISIBLE);
        }
    }

}
