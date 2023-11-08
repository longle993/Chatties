package com.example.chatties.Presenter;

import com.example.chatties.Contract.ILoginContract;
import com.example.chatties.Entity.User;
import com.example.chatties.Model.UserModel;

public class LoginActivityPresenter implements ILoginContract.Presenter {
    UserModel model;
    ILoginContract.View view;

    public LoginActivityPresenter(ILoginContract.View view) {
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void LoginClick(String email, String password) {
        model.Login(email,password,((isSuccess, e, id) -> {
            view.FinishLogin(isSuccess,e,id);
        }));
    }
}
