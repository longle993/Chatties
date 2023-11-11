package com.example.chatties.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatties.Entity.User;
import com.example.chatties.Entity.UserTable;
import com.example.chatties.View.Activity.ChatActivity;
import com.example.chatties.databinding.ItemFragmentContactBinding;
import com.example.chatties.databinding.ItemFragmentSearchOtherBinding;
import com.example.chatties.databinding.ItemFriendSearchBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter {
    Activity activity;
    ArrayList<User> listUser;
    private static final int FRIENDS_CODE = 0;
    private static final int OTHERS_CODE = 1;
    int type;

    public SearchAdapter(Activity activity, ArrayList<User> listUser, int type) {
        this.activity = activity;
        this.listUser = listUser;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFragmentSearchOtherBinding binding = ItemFragmentSearchOtherBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewholderOther(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = listUser.get(position);
        ViewholderOther view = (ViewholderOther) holder;
        view.binding.tvUsername.setText(user.getName());
        Glide.with(activity).load(user.getAvatar()).into(view.binding.imvAvatar);
        view.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ChatActivity.class);
            Bundle sendData = new Bundle();
            sendData.putString(UserTable.USER_ID,user.getId());
            sendData.putString(UserTable.USER_AVATAR, user.getAvatar());
            sendData.putString(UserTable.USER_NAME, user.getName());
            sendData.putString("CurrentID", FirebaseAuth.getInstance().getCurrentUser().getUid());
            sendData.putBoolean(UserTable.USER_STATUS,user.isStatus());
            intent.putExtras(sendData);
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public  class ViewholderFriend extends RecyclerView.ViewHolder {
        ItemFriendSearchBinding binding;
        public ViewholderFriend(@NonNull ItemFriendSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public  class ViewholderOther extends RecyclerView.ViewHolder {
        ItemFragmentSearchOtherBinding binding;
        public ViewholderOther(@NonNull ItemFragmentSearchOtherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
