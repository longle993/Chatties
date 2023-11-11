package com.example.chatties.Presenter.FragmentPresenter;

import com.example.chatties.Contract.IChatViewContract;
import com.example.chatties.Entity.User;
import com.example.chatties.Model.MessageModel;
import com.example.chatties.Model.UserModel;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatsFragmentPresenter implements IChatViewContract.Presenter {
    MessageModel model;
    UserModel userModel;
    IChatViewContract.View view;

    public ChatsFragmentPresenter(IChatViewContract.View view) {
        this.view = view;
        model = new MessageModel();
        userModel = new UserModel();
    }

    @Override
    public void onLoadChatList() {
        model.LoadConverForUser((isSuccess, e, conversation,type) -> {
            if(isSuccess){
                view.onFinishLoadChatList(isSuccess,e,conversation,type);
            }
        });
    }

    @Override
    public void onLoadListUser(ArrayList<String> listID) {
        for(String id : listID){
            userModel.GetUser(id,((isSuccess, e, user) -> {
                view.onFinishLoadUserList(isSuccess,e,user);
            }));
        }
    }
}
