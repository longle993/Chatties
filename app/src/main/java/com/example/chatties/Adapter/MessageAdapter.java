package com.example.chatties.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatties.Entity.Chat;
import com.example.chatties.databinding.ItemReceiverBinding;
import com.example.chatties.databinding.ItemSenderBinding;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;

public class MessageAdapter extends RecyclerView.Adapter {
    Activity activity;
    ArrayList<Chat> listChat;
    String id;
    int ITEM_SEND = 0;
    int ITEM_RECEIVE = 1;

    public MessageAdapter(Activity activity, ArrayList<Chat> listChat, String id) {
        this.activity = activity;
        this.listChat = listChat;
        this.id = id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SEND){
            ItemSenderBinding binding =ItemSenderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return  new SenderViewholder(binding);
        }
        else {
            ItemReceiverBinding binding = ItemReceiverBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return  new ReceiverViewholder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = listChat.get(position);
        if (holder instanceof SenderViewholder) {
            SenderViewholder senderViewholder = (SenderViewholder) holder;
            String timeMessage = GetDate(chat.getMessage_time());
            senderViewholder.binding.tvMessageSender.setText(chat.getMessage());
            senderViewholder.binding.tvTimemessageSender.setText(timeMessage);
            senderViewholder.binding.senderShape.setOnClickListener(v -> {
                chat.setHideTimeMessage(!chat.isHideTimeMessage());
                if (chat.isHideTimeMessage()) {
                    senderViewholder.binding.tvTimemessageSender.setVisibility(View.VISIBLE);
                } else {
                    senderViewholder.binding.tvTimemessageSender.setVisibility(View.GONE);
                }
            });
        } else {
            ReceiverViewholder receiverViewholder = (ReceiverViewholder) holder;
            receiverViewholder.binding.tvMessage.setText(chat.getMessage());
            String timeMessage = GetDate(chat.getMessage_time());
            receiverViewholder.binding.tvTimemessage.setText(timeMessage);
            receiverViewholder.binding.receiverShape.setOnClickListener(v -> {
                chat.setHideTimeMessage(!chat.isHideTimeMessage());
                if (chat.isHideTimeMessage()) {
                    receiverViewholder.binding.tvTimemessage.setVisibility(View.VISIBLE);
                } else {
                    receiverViewholder.binding.tvTimemessage.setVisibility(View.GONE);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return listChat.size();
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat =listChat.get(position);
        if (id.equals(chat.getSenderID())){
            return ITEM_SEND;
        }
        else {
            return ITEM_RECEIVE;
        }
    }

    public class SenderViewholder extends RecyclerView.ViewHolder {
        ItemSenderBinding binding;
        public SenderViewholder(@NonNull ItemSenderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public class ReceiverViewholder extends RecyclerView.ViewHolder {
        ItemReceiverBinding binding;
        public ReceiverViewholder(@NonNull ItemReceiverBinding binding) {
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
