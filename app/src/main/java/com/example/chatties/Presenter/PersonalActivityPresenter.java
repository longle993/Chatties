package com.example.chatties.Presenter;

import com.example.chatties.Contract.IPersonalContract;
import com.example.chatties.Model.UserModel;

public class PersonalActivityPresenter implements IPersonalContract.Presenter {
    IPersonalContract.View view;
    UserModel model;

    public PersonalActivityPresenter(IPersonalContract.View view) {
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void LoadingInfo(String id) {
        model.GetUser(id,((isSuccess, e, user) -> {
            view.onLoadInfo(isSuccess,e,user);
        }));
    }
}
