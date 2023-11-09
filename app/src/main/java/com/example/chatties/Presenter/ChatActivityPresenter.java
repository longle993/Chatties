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
        model.LoadChat(senderID,receiverID,((isSuccess, e, chat) -> {
            if(isSuccess){
                view.onFinishLoadConversation(isSuccess,null,chat);
            }
            else {
                view.onFinishLoadConversation(isSuccess,e,null);
            }

        }));
    }

    @Override
    public void SendMessage(Chat chat,String receiverID) {
        model.SendMessage(chat,receiverID);
    }

    @Override
    public void GetStatus(String id) {
        userModel.GetStatus(id,status -> {
            view.ShowStatus(status);
        });
    }
}
