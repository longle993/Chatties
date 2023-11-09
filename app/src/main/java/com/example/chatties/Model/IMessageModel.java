package com.example.chatties.Model;

import android.net.Uri;

import com.example.chatties.Entity.Chat;
import com.example.chatties.Entity.Conversation;
import com.example.chatties.Entity.User;
import com.google.firebase.Timestamp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface IMessageModel {
    void LoadConversation(String senderID, String receiverID, onLoadConverListener listener);
    void LoadConverForUser(onLoadConverListener listener);
    void LoadChat(String senderID, String receiverID,onLoadChatListener listener);
    void CreateConversation(String senderID, String receiverID,Chat chat);
    void SendMessage(Chat chat, String receiverID);
    void SendImage(ArrayList<Uri> listImage,String receiverID, onUploadImage listener);
    void UpdateConversation(Chat chat);
    interface onLoadConverListener{
        void onFinish(boolean isSuccess, Exception e, Conversation conversation, int type);
    }
    interface onLoadChatListener{
        void onFinish(boolean isSuccess, Exception e, Chat message);
    }
    interface onUploadImage{
        void onFinish(boolean isSuccess, Exception e);
    }


}

