package com.example.chatties.Presenter.FragmentPresenter;

import com.example.chatties.Adapter.PagerAdapter;
import com.example.chatties.Contract.IRequestContract;
import com.example.chatties.Model.UserModel;

public class SendRequestFragmentPresenter implements IRequestContract.Presenter {
    IRequestContract.View view;
    UserModel model;

    public SendRequestFragmentPresenter(IRequestContract.View view) {
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void GetRequest() {
        model.getSendRequestFriend((listUser, e) -> {
            view.onFinishGetRequest(true,e,listUser);
        });
    }

    @Override
    public void ReplyRequest(String id,boolean isAccept) {
        model.removeRequestFriend(id,e -> {
            view.updateRequest();
        });
    }
}
