package com.example.chatties.Presenter;

import android.net.Uri;

import com.example.chatties.Contract.ISignUpContract;
import com.example.chatties.Entity.User;
import com.example.chatties.Model.UserModel;

public class SignUpActivityPresenter implements ISignUpContract.Presenter {
    ISignUpContract.View view;
    UserModel model;

    public SignUpActivityPresenter(ISignUpContract.View view) {
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void onSignUpClicked(User user, Uri uri) {
        model.Register(uri,user,((isSuccess, e) -> {
            view.onFinish(isSuccess,e);
        }));
    }
}
