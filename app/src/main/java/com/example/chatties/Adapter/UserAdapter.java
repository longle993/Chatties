package com.example.chatties.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatties.Entity.Conversation;
import com.example.chatties.Entity.User;
import com.example.chatties.Entity.UserTable;
import com.example.chatties.View.Activity.ChatActivity;
import com.example.chatties.databinding.ItemFragmentChatsBinding;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        String timeMessage = GetDate(conver.getLast_message_time());
        view.binding.lastTimeMessage.setText(timeMessage);
        view.binding.userNameChats.setText(user.getName());
        Glide.with(activity).load(user.getAvatar()).into(view.binding.avatar);
        view.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ChatActivity.class);
            Bundle sendData = new Bundle();
            sendData.putString(UserTable.USER_ID,user.getId());
            sendData.putString(UserTable.USER_AVATAR, user.getAvatar());
            sendData.putString(UserTable.USER_NAME, user.getName());
            sendData.putBoolean(UserTable.USER_STATUS,user.isStatus());
            intent.putExtras(sendData);
            activity.startActivity(intent);
        });
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
    private String GetDate(Timestamp timeMess) {
        Date date = timeMess.toDate();
        Calendar dateMess = Calendar.getInstance();
        dateMess.setTime(date);

        Calendar today = Calendar.getInstance();
        SimpleDateFormat formatDate;

        if (dateMess.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && dateMess.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                && dateMess.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
            formatDate = new SimpleDateFormat("hh:mm a");
        } else {
            formatDate = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        }

        String timeMessage = formatDate.format(date);
        return timeMessage;
    }
}
