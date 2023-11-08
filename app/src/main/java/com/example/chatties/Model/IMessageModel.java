package com.example.chatties.Model;

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
    void CreateConversation(String senderID, String receiverID,Chat chat,onCreateConverListener listener);
    void SendMessage(Chat chat, String receiverID, onSendingListener listener);
    interface onLoadConverListener{
        void onFinish(boolean isSuccess, Exception e, Conversation conversation);
    }
    interface onLoadListChat{
        void onFinish(boolean isSuccess, Exception e, Conversation conversation, ArrayList<User> listUser);

    }
    interface onLoadChatListener{
        void onFinish(boolean isSuccess, Exception e, Chat message);
    }
    interface onCreateConverListener{
        void onCreate(boolean isSuccess, Exception e, Conversation newConver);
    }
    interface onSendingListener{
        void onFinish(boolean isSuccess, Exception e,Chat newMessage);
    }

}

