package com.example.chatties.Presenter;

import com.example.chatties.Contract.IStatusContract;
import com.example.chatties.Model.UserModel;

public class MainActivityPresenter implements IStatusContract.Presenter {
    UserModel model;
    IStatusContract.View view;

    public MainActivityPresenter(IStatusContract.View view){
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void SetStatus(boolean isActive) {
        model.SetStatus(isActive);
    }
}
