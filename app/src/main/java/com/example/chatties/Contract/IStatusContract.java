package com.example.chatties.Contract;

public interface IStatusContract {
    interface View{
    }
    interface Presenter{
        void SetStatus(boolean isActive);
    }
}
