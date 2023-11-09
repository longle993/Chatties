package com.example.chatties.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatties.Entity.Chat;
import com.example.chatties.R;
import com.example.chatties.databinding.ItemImageReceiverBinding;
import com.example.chatties.databinding.ItemImageSenderBinding;
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
    int ITEM_IMAGE_SEND = 2;
    int ITEM_IMAGE_RECEIVE = 3;

    public MessageAdapter(Activity activity, ArrayList<Chat> listChat, String id) {
        this.activity = activity;
        this.listChat = listChat;
        this.id = id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:ItemSenderBinding binding =ItemSenderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
                return  new SenderViewholder(binding);
            case 1:ItemReceiverBinding bindingReceive = ItemReceiverBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
                return  new ReceiverViewholder(bindingReceive);
            case 2:ItemImageSenderBinding bindingIMGsend = ItemImageSenderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
                return new ImageSenderViewholder(bindingIMGsend);
            case 3:ItemImageReceiverBinding bindingIMGreceive = ItemImageReceiverBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
                return new ImageReceiverViewholder(bindingIMGreceive);
            default:return null;
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
        } else if(holder instanceof ReceiverViewholder) {
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
        else if(holder instanceof ImageSenderViewholder){
            ImageSenderViewholder imgSender = (ImageSenderViewholder) holder;
            Glide.with(activity).load(chat.getMessage()).override(100,150).into(imgSender.binding.imageView);
            imgSender.itemView.setOnClickListener(v -> {
                showImageDialog(activity,chat.getMessage());
            });
        }
        else {
            ImageReceiverViewholder imgReceiver = (ImageReceiverViewholder) holder;
            Glide.with(activity).load(chat.getMessage()).override(100,150).into(imgReceiver.binding.imageView);
            imgReceiver.itemView.setOnClickListener(v -> {
                showImageDialog(activity, chat.getMessage());
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
        if (id.equals(chat.getSenderID()) && !chat.isImage()){
            return ITEM_SEND;
        }
        else if(!id.equals(chat.getSenderID()) && !chat.isImage()){
            return ITEM_RECEIVE;
        } else if (id.equals(chat.getSenderID()) && chat.isImage()) {
            return ITEM_IMAGE_SEND;
        }
        else {
            return ITEM_IMAGE_RECEIVE;
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
    public class ImageSenderViewholder extends RecyclerView.ViewHolder {
        ItemImageSenderBinding binding;
        public ImageSenderViewholder(@NonNull ItemImageSenderBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
    public class ImageReceiverViewholder extends RecyclerView.ViewHolder {
        ItemImageReceiverBinding binding;
        public ImageReceiverViewholder(@NonNull ItemImageReceiverBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
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
    // Phương thức để hiển thị hình ảnh ở chế độ xem lớn hơn
    private void showImageDialog(Context context, String imageURI) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_dialog_layout);

        ImageView imageViewDialog = dialog.findViewById(R.id.imageViewDialog);
        Glide.with(activity).load(imageURI).into(imageViewDialog);
        dialog.show();
    }
}
