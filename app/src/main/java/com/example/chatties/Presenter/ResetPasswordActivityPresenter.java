package com.example.chatties.Presenter;

import com.example.chatties.Contract.IResetPassContract;
import com.example.chatties.Model.UserModel;

public class ResetPasswordActivityPresenter implements IResetPassContract.Presenter {
    IResetPassContract.View view;
    UserModel model;

    public ResetPasswordActivityPresenter(IResetPassContract.View view) {
        this.model = new UserModel();
        this.view = view;
    }

    @Override
    public void SendEmailReset(String email) {
        model.ResetPass(email,((isSuccess, e) -> {
            view.onFinishSending(isSuccess,e);
        }));
    }
}
