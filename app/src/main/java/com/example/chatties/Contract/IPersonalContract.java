package com.example.chatties.Contract;

import com.example.chatties.Entity.User;

public interface IPersonalContract {
    interface View{
        void onLoadInfo(boolean isSuccess, Exception e, User user);
    }
    interface Presenter{
        void LoadingInfo(String id);
    }
}
