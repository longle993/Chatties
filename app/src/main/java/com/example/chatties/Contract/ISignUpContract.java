package com.example.chatties.Contract;

import android.net.Uri;

import com.example.chatties.Entity.User;

public interface ISignUpContract {
    interface View{
        void onFinish(boolean isSuccess, Exception e);
    }
    interface Presenter{
        void onSignUpClicked(User user, Uri uri);
    }
}
