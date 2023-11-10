package com.example.chatties.Contract;

public interface IResetPassContract {
    interface View{
        void onFinishSending(boolean isSuccess, Exception e);
    }
    interface Presenter{
        void SendEmailReset(String email);
    }
}
