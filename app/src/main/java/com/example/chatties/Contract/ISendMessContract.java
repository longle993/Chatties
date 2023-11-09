package com.example.chatties.Contract;

import android.net.Uri;

import com.example.chatties.Entity.Chat;

import java.util.ArrayList;

public interface ISendMessContract {
    interface View{
        void onFinishLoadConversation(boolean isSuccess , Exception e, Chat message);
        void ShowStatus(boolean status);
        void onFinishSendPicture(boolean isSuccess, Exception e);
    }
    interface Presenter{
        void LoadConversation(String senderID, String receiverID);
        void SendMessage(Chat chat, String receiverID);
        void SendPicture(ArrayList<Uri> listImage, String receiverID);
        void GetStatus(String id);
    }
}
