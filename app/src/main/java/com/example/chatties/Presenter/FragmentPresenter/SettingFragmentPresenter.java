package com.example.chatties.Presenter.FragmentPresenter;

import com.example.chatties.Contract.ISettingContract;
import com.example.chatties.Model.UserModel;

public class SettingFragmentPresenter implements ISettingContract.Presenter {
    UserModel model;
    ISettingContract.View view;

    public SettingFragmentPresenter(ISettingContract.View view) {
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void LoadUserInfo(String id) {
        model.GetUser(id,((isSuccess, e, user) -> {
            view.onLoadInfo(isSuccess,e,user);
        }));
    }

    @Override
    public void Logout() {
        model.Logout((isSuccess, e) -> {
            view.onLogout(isSuccess,e);
        });
    }
}
