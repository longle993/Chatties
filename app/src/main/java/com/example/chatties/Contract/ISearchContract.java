package com.example.chatties.Contract;

import com.example.chatties.Entity.User;

import java.util.ArrayList;

public interface ISearchContract {
    interface View{
        void onGetSearchResult(boolean isSuccess, ArrayList<User> listUser);
        void onFinishGetListFriends(boolean isSuccess, ArrayList<User> userID);
    }
    interface Presenter{
        void onSearching(String queryText);
        void onGetListFriends();
    }
}
