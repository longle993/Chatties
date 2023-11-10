package com.example.chatties.Presenter;

import com.example.chatties.Contract.IPersonalContract;
import com.example.chatties.Entity.User;
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
        model.getSendRequestFriend(((listUser, e) -> {
            if(listUser.size() > 0 && listUser != null){
                for(User user : listUser){
                    if(user.getId().equals(id)){
                        view.checkRequest(true);
                        break;
                    }
                }
            }

        }));
    }

    @Override
    public void SendRequest(String friendID) {
        model.sendRequestFriend(friendID,e -> {
            view.onFinishSendRequest();
        });
    }
}
