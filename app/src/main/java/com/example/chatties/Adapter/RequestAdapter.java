package com.example.chatties.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatties.Entity.User;
import com.example.chatties.databinding.FragmentReceiverequestBinding;
import com.example.chatties.databinding.FragmentSendrequestBinding;
import com.example.chatties.databinding.ItemFragmentFriendsreceiveBinding;
import com.example.chatties.databinding.ItemFragmentFriendssendBinding;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter {
    ArrayList<User> listUserRQ;
    Activity activity;
    int type;
    private static final int SEND_RQ = 0;
    private static final int RECEIVE_RQ = 1;

    public RequestAdapter(ArrayList<User> listUserRQ, Activity activity, int type) {
        this.listUserRQ = listUserRQ;
        this.activity = activity;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SEND_RQ){
            ItemFragmentFriendssendBinding binding = ItemFragmentFriendssendBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return  new ViewholerSend(binding);
        }
        else {
            ItemFragmentFriendsreceiveBinding binding = ItemFragmentFriendsreceiveBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return  new ViewholderReceive(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = listUserRQ.get(position);
        if(holder instanceof ViewholerSend){
            ViewholerSend view = (ViewholerSend) holder;
            view.binding.userNameChats.setText(user.getName());
            Glide.with(activity).load(user.getAvatar()).into(view.binding.avatar);
        }
        else {
            ViewholderReceive view = (ViewholderReceive) holder;
            view.binding.userNameChats.setText(user.getName());
            Glide.with(activity).load(user.getAvatar()).into(view.binding.avatar);
        }
    }

    @Override
    public int getItemCount() {
        return listUserRQ.size();
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    public class ViewholerSend extends RecyclerView.ViewHolder {
        ItemFragmentFriendssendBinding binding;
        public ViewholerSend(@NonNull ItemFragmentFriendssendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class ViewholderReceive extends RecyclerView.ViewHolder{
        ItemFragmentFriendsreceiveBinding binding;

        public ViewholderReceive(@NonNull ItemFragmentFriendsreceiveBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
