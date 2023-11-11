package com.example.chatties.Model;

import android.net.Uri;

import com.example.chatties.Entity.User;

import java.util.ArrayList;

public interface IUserModel {
    void Logout(onFinishSignout listenter);
    void Login(String email, String password, onFinishLoginListener listener);
    void Register(Uri avatar, User user, onFinishRegisterListener listener);
    void ResetPass(String email, SendEmailResetListener listener);
    void GetListFriendID(String id, onGetListFriendsID listener);
    void GetStatus(String id,onSetActiveListener listener);
    void SetStatus(boolean isActive);
    void getRequestFriend(onFinishGetListUserListener listener);
    void getSendRequestFriend(onFinishGetListUserListener listener);
    void sendRequestFriend(String friendUserID, onFinishSendRequestFriendListener listener);
    void deleteFriend(String friendUserID, onFinishChangeFriendStatusListener listener);
    void acceptRequestFriend(String friendUserID, onFinishChangeFriendRequestStatusListener listener);
    void denyRequestFriend(String friendUserID, onFinishChangeFriendStatusListener listener);
    void removeRequestFriend(String friendUserID, onFinishSendRequestFriendListener listener);
    void performSearch(String queryText, onGetSearchResult listener);

    interface onFinishGetListUserListener{
        void onFinishGetRequestFriend(ArrayList<User> listUser,Exception e);
    }
    interface onFinishSendRequestFriendListener{
        void onFinishSendRequest(Exception e);
    }
    interface onFinishChangeFriendStatusListener{
        void onFinishChangeFriendStatus(Exception e);
    }

    interface onFinishChangeFriendRequestStatusListener{
        void onFinishChangeFriendRequest(Exception e);

    }
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
    interface SendEmailResetListener{
        void onFinishSendEmaiReset(boolean isSuccess, Exception e);
    }
    interface onGetSearchResult{
        void onGetResult(boolean isSuccess, ArrayList<User> listUser);
    }
}
