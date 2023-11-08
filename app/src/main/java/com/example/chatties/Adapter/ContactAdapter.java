package com.example.chatties.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatties.Entity.User;
import com.example.chatties.Entity.UserTable;
import com.example.chatties.Model.UserModel;
import com.example.chatties.R;
import com.example.chatties.View.ChatActivity;
import com.example.chatties.databinding.ItemFragmentContactBinding;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter {
    ArrayList<User> friendsID;
    Activity activity;
    UserModel model;
    String currentID;

    public ContactAdapter(ArrayList<User> friendsID, Activity activity, String currentID) {
        this.friendsID = friendsID;
        this.activity = activity;
        this.currentID = currentID;
        model = new UserModel();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFragmentContactBinding binding = ItemFragmentContactBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ContactAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = friendsID.get(position);
        ViewHolder view = (ViewHolder) holder;
        view.binding.tvUsername.setText(user.getName());
        Glide.with(activity).load(user.getAvatar()).into(view.binding.imvAvatar);
        Glide.with(activity).load(R.drawable.active).into(view.binding.imvStatus);
        view.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ChatActivity.class);
            Bundle sendData = new Bundle();
            sendData.putString(UserTable.USER_ID,user.getId());
            sendData.putString(UserTable.USER_AVATAR, user.getAvatar());
            sendData.putString(UserTable.USER_NAME, user.getName());
            sendData.putString("CurrentID",currentID);
            sendData.putBoolean(UserTable.USER_STATUS,user.isStatus());
            intent.putExtras(sendData);
            activity.startActivity(intent);
            });
    }

    @Override
    public int getItemCount() {
        return friendsID.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    ItemFragmentContactBinding binding;
        public ViewHolder(@NonNull ItemFragmentContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
