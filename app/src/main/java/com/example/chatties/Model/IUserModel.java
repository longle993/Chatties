package com.example.chatties.Model;

import android.net.Uri;

import com.example.chatties.Entity.User;

import java.util.ArrayList;

public interface IUserModel {
    void Logout(onFinishSignout listenter);
    void Login(String email, String password, onFinishLoginListener listener);
    void Register(Uri avatar, User user, onFinishRegisterListener listener);
    void GetListFriendID(String id, onGetListFriendsID listener);
    void GetStatus(String id,onSetActiveListener listener);
    void SetStatus(boolean isActive);
    interface onFinishSignout{
        void onFinish(boolean isSuccess, Exception e);
    }
    interface onFinishRegisterListener{
        void onFinish(boolean isSuccess, Exception e);
    }
    interface onFinishLoginListener{
        void onFinish(boolean isSuccess, Exception e,String id);
    }
    interface onGetUserListener{
        void onFinishGet(boolean isSuccess, Exception e, User user);
    }
    interface onGetListFriendsID{
        void onFinish(boolean isSuccess, Exception e, ArrayList<User> listFriendsID);
    }
    interface onSetActiveListener{
        void onSet(boolean status);
    }
}
