package com.example.chatties.Contract;

import com.example.chatties.Entity.Conversation;
import com.example.chatties.Entity.User;

import java.util.ArrayList;

public interface IChatViewContract {
    interface View{
        void onFinishLoadChatList(boolean isSuccess, Exception e, Conversation conver);
        void onFinishLoadUserList(boolean isSuccess, Exception e, User user);
    }
    interface Presenter{
        void onLoadChatList();
        void onLoadListUser(ArrayList<String> listID);
    }
}
