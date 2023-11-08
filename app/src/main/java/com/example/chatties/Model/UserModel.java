package com.example.chatties.Model;

import android.net.Uri;
import android.widget.ArrayAdapter;

import androidx.core.content.ContextCompat;

import com.example.chatties.Entity.User;
import com.example.chatties.Entity.UserTable;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Set;

public class UserModel implements IUserModel{
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;
    public UserModel(){
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    @Override
    public void Logout(onFinishSignout listener) {
        if(auth!=null){
            auth.signOut();
            listener.onFinish(true,null);
        }

    }

    @Override
    public void Login(String email, String password, onFinishLoginListener listener) {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
                if(auth.getCurrentUser() != null && auth.getCurrentUser().isEmailVerified()){
                    listener.onFinish(true,new Exception("Đăng nhập thành công"),auth.getCurrentUser().getUid());
                }
                else {
                    listener.onFinish(false,new Exception("Chưa xác thực tài khoản"),null);
                }
           }
           else {
               listener.onFinish(false,task.getException(),null);
           }
        });
    }

    @Override
    public void Register(Uri avatar, User user, onFinishRegisterListener listener) {
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String id = task.getResult().getUser().getUid();
                        StorageReference storageReference = storage.getReference().child("Avatar").child(id);

                        //Gửi avatar lên Storage
                        if(avatar != null){
                            storageReference.putFile(avatar).addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful()){
                                    //Lấy đường dẫn của ảnh
                                    storageReference.child("Avatar/"+id);
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String imgURL = uri.toString();
                                                    user.setAvatar(imgURL);

                                                    //Gửi dữ liệu lên FireStore
                                                    user.setId(id);
                                                    db.collection(UserTable.USER_TABLENAME)
                                                            .document(task.getResult().getUser().getUid())
                                                            .set(user);
                                                }
                                            });
                                }
                            });
                        }
                        else {
                            user.setAvatar("https://firebasestorage.googleapis.com/v0/b/chat-application-50b70.appspot.com/o/default-avatar.png?alt=media&token=bc8b4f65-e185-4f25-aa0d-4a87660a5505");
                            user.setId(id);
                            db.collection(UserTable.USER_TABLENAME)
                                    .document(task.getResult().getUser().getUid())
                                    .set(user);
                        }
                        //Trả về thông báo thành công
                        Exception e = new Exception("Tạo tài khoản thành công");
                        listener.onFinish(true,e);

                        //Gửi mail xác thực
                        auth.getCurrentUser().sendEmailVerification();
                    }
                })
                .addOnFailureListener(e -> {
                    if(e instanceof FirebaseAuthUserCollisionException){
                        Exception ex = new Exception("Tài khoản đã tồn tại");
                        listener.onFinish(false,ex);
                    }
                    else {
                        Exception ex = new Exception("Có lỗi xảy ra! Đăng ký thất bại");
                        listener.onFinish(false,ex);
                    }
                });
    }

    @Override
    public void GetListFriendID(String id,onGetListFriendsID listener) {
        ArrayList<User> listFriendsID = new ArrayList<>();
        GetUser(id,((isSuccess, e, user) -> {
            if (isSuccess){
                ArrayList<String> listID = user.getListfriends();
                if(listID !=null){
                    db.collection(UserTable.USER_TABLENAME).whereIn(UserTable.USER_ID,listID).get().addOnCompleteListener(task -> {
                        for(QueryDocumentSnapshot document: task.getResult()){
                            User getUser = new User();
                            getUser.setId(document.getId());
                            getUser.setName(document.getString(UserTable.USER_NAME));
                            getUser.setAvatar(document.getString(UserTable.USER_AVATAR));
                            listFriendsID.add(getUser);
                        }
                        listener.onFinish(isSuccess,e,listFriendsID);
                    });
                }
                else {
                    listener.onFinish(false,e,null);
                }
            }

        }));

    }

    @Override
    public void GetStatus(String id, onSetActiveListener listener) {
        db.collection(UserTable.USER_TABLENAME)
                .whereEqualTo(UserTable.USER_ID,id)
                .addSnapshotListener(((querySnapshot, error) -> {
            if(error!=null){
                listener.onSet(false); return;
            }
            if(querySnapshot != null) {
                for(DocumentChange dc: querySnapshot.getDocumentChanges()) {
                    if(dc.getType() == DocumentChange.Type.MODIFIED){
                        listener.onSet(dc.getDocument().getBoolean(UserTable.USER_STATUS));
                    }
                    else if(dc.getType() == DocumentChange.Type.ADDED){
                        listener.onSet(dc.getDocument().getBoolean(UserTable.USER_STATUS));
                    }
                }
            }
        }));
    }

    @Override
    public void SetStatus(boolean isActive) {
        db.collection(UserTable.USER_TABLENAME)
                .document(auth.getCurrentUser().getUid())
                .update(UserTable.USER_STATUS,isActive);
    }

    public void GetUser(String id, onGetUserListener listener){
        User user = new User();
        db.collection(UserTable.USER_TABLENAME).document(id).get()
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       DocumentSnapshot document = task.getResult();
                       user.setId(document.getId());
                       user.setPassword(document.getString(UserTable.USER_PASSWORD));
                       user.setEmail(document.getString(UserTable.USER_EMAIL));
                       user.setName(document.getString(UserTable.USER_NAME));
                       user.setAvatar(document.getString(UserTable.USER_AVATAR));
                       user.setListfriends((ArrayList<String>) document.get(UserTable.USER_FRIENDS));
                       listener.onFinishGet(true,null,user);
                   }
                   else {
                       listener.onFinishGet(false,task.getException(),null);
                   }
                });
    }


}
