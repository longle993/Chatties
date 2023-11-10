package com.example.chatties.Presenter;

import com.example.chatties.Contract.IStatusContract;
import com.example.chatties.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    public void GetUser() {
        model.GetUser(FirebaseAuth.getInstance().getUid(), ((isSuccess, e, user) -> {
            view.LoadUser(user);
        }));
    }
}
