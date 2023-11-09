package com.example.chatties.Contract;

import com.example.chatties.Entity.Chat;

import java.util.ArrayList;

public interface ISendMessContract {
    interface View{
        void onFinishLoadConversation(boolean isSuccess , Exception e, Chat message);
        void ShowStatus(boolean status);
    }
    interface Presenter{
        void LoadConversation(String senderID, String receiverID);
        void SendMessage(Chat chat, String receiverID);
        void GetStatus(String id);
    }
}
