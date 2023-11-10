package com.example.chatties.Presenter;

import com.example.chatties.Contract.IRequestContract;
import com.example.chatties.Model.UserModel;

public class ReceiveRequestFragmentPresenter implements IRequestContract.Presenter {
    IRequestContract.View view;
    UserModel model;

    public ReceiveRequestFragmentPresenter(IRequestContract.View view) {
        model = new UserModel();
        this.view = view;
    }

    @Override
    public void GetRequest() {
        model.getRequestFriend((listUser, e) -> {
            view.onFinishGetRequest(true,e,listUser);
        });
    }
}
