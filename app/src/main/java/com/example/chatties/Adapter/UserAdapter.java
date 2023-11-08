package com.example.chatties.Adapter;

import android.app.Activity;
import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatties.Entity.Chat;
import com.example.chatties.Entity.Conversation;
import com.example.chatties.Entity.User;
import com.example.chatties.databinding.ItemFragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter{
    Activity activity;
    ArrayList<Conversation> listConver;

    User user;
    public UserAdapter(Activity activity, ArrayList<Conversation> listConver,User user) {
        this.activity = activity;
        this.listConver = listConver;
        this.user = user;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFragmentChatsBinding binding = ItemFragmentChatsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new UserAdapter.Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Conversation conver = listConver.get(position);
        Viewholder view = (Viewholder) holder;
        view.binding.lastMessage.setText(conver.getLast_message());
        view.binding.userNameChats.setText(user.getName());
        Glide.with(activity).load(user.getAvatar()).into(view.binding.avatar);

    }

    @Override
    public int getItemCount() {
        return listConver.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ItemFragmentChatsBinding binding;
        public Viewholder(@NonNull ItemFragmentChatsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
