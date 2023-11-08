package com.example.chatties.Contract;

import com.example.chatties.Entity.User;

public interface ISettingContract {
    interface View{
        void onLoadInfo(boolean isSuccess,Exception e, User user);
        void onLogout(boolean isSuccess, Exception e);
    }
    interface Presenter{
        void LoadUserInfo(String id);
        void Logout();
    }
}
