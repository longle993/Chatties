package com.example.chatties.Contract;

import com.example.chatties.Entity.User;

import java.util.ArrayList;

public interface IRequestContract {
    interface View{
        void onFinishGetRequest(boolean isSuccess, Exception e, ArrayList<User> user);
    }
    interface Presenter{
        void GetRequest();
    }
}
