package com.example.chatties.Contract;

import com.example.chatties.Entity.User;

public interface ILoginContract {
    interface View{
        void FinishLogin(boolean isSuccess, Exception e, String id);
    }
    interface Presenter{
        void LoginClick(String email,String password);
    }
}
