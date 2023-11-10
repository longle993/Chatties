package com.example.chatties.Contract;

import com.example.chatties.Entity.User;

public interface IStatusContract {
    interface View{
        void LoadUser(User user);
    }
    interface Presenter{
        void SetStatus(boolean isActive);
        void GetUser();
    }
}
