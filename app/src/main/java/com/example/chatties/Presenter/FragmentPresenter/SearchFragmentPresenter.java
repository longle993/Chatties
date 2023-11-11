package com.example.chatties.Presenter.FragmentPresenter;

import com.example.chatties.Contract.ISearchContract;
import com.example.chatties.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;

public class SearchFragmentPresenter implements ISearchContract.Presenter {
    ISearchContract.View view;
    UserModel model;

    public SearchFragmentPresenter(ISearchContract.View view) {
        model = new UserModel();
        this.view = view;
    }

    @Override
    public void onSearching(String queryText) {
        model.performSearch(queryText,((isSuccess, listUser) -> {
            if(isSuccess){
                view.onGetSearchResult(isSuccess,listUser);
            }
            else {
                view.onGetSearchResult(isSuccess,listUser);
            }
        }));
    }

    @Override
    public void onGetListFriends() {
        model.GetListFriendID(FirebaseAuth.getInstance().getUid(), ((isSuccess, e, listFriendsID) -> {
            view.onFinishGetListFriends(isSuccess,listFriendsID);
        }));
    }
}
