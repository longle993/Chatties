package com.example.chatties.Presenter;

import com.example.chatties.Contract.ISendMessContract;
import com.example.chatties.Entity.Chat;
import com.example.chatties.Model.MessageModel;
import com.example.chatties.Model.UserModel;

public class ChatActivityPresenter implements ISendMessContract.Presenter {
    MessageModel model;
    UserModel userModel;
    ISendMessContract.View view;

    public ChatActivityPresenter(ISendMessContract.View view) {
        this.view = view;
        model = new MessageModel();
        userModel = new UserModel();
    }

    @Override
    public void LoadConversation(String senderID, String receiverID) {
        model.LoadChat(senderID,receiverID,((isSuccess, e, listChat) -> {
            if(isSuccess){
                view.onFinishLoadConversation(isSuccess,null,listChat);
            }
            else {
                view.onFinishLoadConversation(isSuccess,e,null);
            }
        }));
    }

    @Override
    public void SendMessage(Chat chat,String receiverID) {
        model.SendMessage(chat,receiverID,((isSuccess, e,chat1) -> {
            if(isSuccess){
                view.onReloadMessage(isSuccess,e,chat1);
            }
            else {
                view.onReloadMessage(isSuccess,e,null);
            }
        }));
    }

    @Override
    public void GetStatus(String id) {
        userModel.GetStatus(id,status -> {
            view.ShowStatus(status);
        });
    }
}
