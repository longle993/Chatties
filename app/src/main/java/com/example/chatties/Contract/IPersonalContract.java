package com.example.chatties.Contract;

import com.example.chatties.Entity.User;

public interface IPersonalContract {
    interface View{
        void onLoadInfo(boolean isSuccess, Exception e, User user);
        void onFinishSendRequest();
        void checkRequest(boolean isSend);
    }
    interface Presenter{
        void LoadingInfo(String id);
        void SendRequest(String friendID);
    }
}
