package com.example.chatties.Contract;

import com.example.chatties.Entity.User;

import java.util.ArrayList;

public interface IContactContract {
    interface View{
        void LoadItemFriend(boolean isSuccess, Exception e, ArrayList<User> friendsID);
    }
    interface Presenter{
        void onLoadListFriends(String currentID);
    }
}
