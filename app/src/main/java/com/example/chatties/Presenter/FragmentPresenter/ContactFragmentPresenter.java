package com.example.chatties.Presenter.FragmentPresenter;

import com.example.chatties.Contract.IContactContract;
import com.example.chatties.Model.UserModel;

public class ContactFragmentPresenter implements IContactContract.Presenter {
    IContactContract.View view;
    UserModel model;

    public ContactFragmentPresenter(IContactContract.View view) {
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void onLoadListFriends(String currentID) {
        model.GetListFriendID(currentID,((isSuccess, e, listFriendsID) -> {
            if(isSuccess){
                view.LoadItemFriend(isSuccess,e,listFriendsID);
            }
            else {
                view.LoadItemFriend(isSuccess,e,listFriendsID);
            }
        }));
    }
}
