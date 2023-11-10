package com.example.chatties.Model;

import android.app.Application;
import android.net.Uri;

import com.example.chatties.Entity.Chat;
import com.example.chatties.Entity.ChatTable;
import com.example.chatties.Entity.Conversation;
import com.example.chatties.Entity.ConversationTable;
import com.example.chatties.Entity.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class MessageModel implements IMessageModel{
    FirebaseFirestore db;
    FirebaseStorage storage;
    UserModel userModel;

    public MessageModel(){
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        userModel = new UserModel();
    }
    @Override
    public void LoadConversation(String senderID, String receiverID, onLoadConverListener listener) {
            db.collection(ConversationTable.CONVERSATION_TABLENAME)
                    .whereArrayContains(ConversationTable.CONVERSATION_USER, FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if(task.getResult() == null || task.getResult().isEmpty()){
                                listener.onFinish(false, new Exception("Kết quả truy vấn trống"), null, ConversationTable.CONVERSATION_NULL);
                                return;
                            }
                            if (task.getResult() != null) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        ArrayList<String> listUser = (ArrayList<String>) document.get(ConversationTable.CONVERSATION_USER);
                                        for (int i = 0; i < listUser.size(); i++) {
                                            if (receiverID.equals(listUser.get(i))) {
                                                Conversation conversation = new Conversation();
                                                conversation.setConversationID(document.getId());
                                                conversation.setLast_message(document.getString(ConversationTable.CONVERSATION_LASTMESSAGE));
                                                conversation.setLast_message_time(document.getTimestamp(ConversationTable.CONVERSATION_LASTMESSAGETIME));
                                                conversation.setUser(listUser);
                                                listener.onFinish(true, null, conversation, ConversationTable.CONVERSATION_ADD);
                                            }
                                        }
                                    }
                            }
                        } else {
                            // Xử lý lỗi trong quá trình thực hiện truy vấn
                            listener.onFinish(false, task.getException(), null, ConversationTable.CONVERSATION_NULL);
                        }
                    });



    }


    @Override
    public void LoadConverForUser(onLoadConverListener listener) {
        db.collection(ConversationTable.CONVERSATION_TABLENAME)
                .whereArrayContains(ConversationTable.CONVERSATION_USER, FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addSnapshotListener((querySnapshot, error) -> {
                    if (error != null) {
                        // Xử lý lỗi ở đây
                        return;
                    }
                    if (querySnapshot != null) {
                        for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                            Conversation conversation = new Conversation();
                            conversation.setConversationID(dc.getDocument().getId());
                            conversation.setLast_message(dc.getDocument().getString(ConversationTable.CONVERSATION_LASTMESSAGE));
                            conversation.setLast_message_time(dc.getDocument().getTimestamp(ConversationTable.CONVERSATION_LASTMESSAGETIME));
                            conversation.setUser((ArrayList<String>) dc.getDocument().get(ConversationTable.CONVERSATION_USER));

                            ArrayList<User> listUser = new ArrayList<>();
                            for (int i = 0; i < conversation.getUser().size(); i++) {
                                userModel.GetUser(conversation.getUser().get(i), (isSuccess, e, user) -> {
                                    listUser.add(user);
                                    // Kiểm tra xem đã lấy đủ dữ liệu User chưa
                                    if (listUser.size() == conversation.getUser().size()) {
                                        if(dc.getType() == DocumentChange.Type.MODIFIED){
                                            listener.onFinish(true, null, conversation,ConversationTable.CONVERSATION_MODIFY); return;
                                        }
                                        if(dc.getType() == DocumentChange.Type.ADDED){
                                            listener.onFinish(true, null, conversation,ConversationTable.CONVERSATION_ADD);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });

    }

    @Override
    public void LoadChat(String senderID, String receiverID, onLoadChatListener listener) {
        LoadConversation(senderID, receiverID, (isSuccess, e, conversation,type) -> {
            if (isSuccess) {
                db.collection(ChatTable.CHAT_TABLENAME)
                        .whereEqualTo(ChatTable.CHAT_CONVERSATIONID, conversation.getConversationID())
                        .addSnapshotListener((querySnapshot, error) -> {
                            if (error != null) {
                                // Xử lý lỗi ở đây
                                listener.onFinish(false, error,null);
                                return;
                            }
                            if (querySnapshot != null) {
                                for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                                    String messageID = dc.getDocument().getId();
                                    String conversationID = dc.getDocument().getString(ChatTable.CHAT_CONVERSATIONID);
                                    String message = dc.getDocument().getString(ChatTable.CHAT_MESSAGE);
                                    Timestamp messageTime = dc.getDocument().getTimestamp(ChatTable.CHAT_MESSTIME);
                                    String id = dc.getDocument().getString(ChatTable.CHAT_SENDERID);
                                    boolean isImage = dc.getDocument().getBoolean(ChatTable.CHAT_ISIMAGE);
                                    Chat chat = new Chat(conversationID, messageID, message, messageTime, id,isImage);
                                    if (dc.getType() == DocumentChange.Type.ADDED ) {
                                        listener.onFinish(true, null, chat);
                                    }
                                }
                            }
                        });
            }
            else {
                listener.onFinish(isSuccess,e,null);
            }
        });

    }

    @Override
    public void CreateConversation(String senderID, String receiverID,Chat chat) {
        Conversation conversation = new Conversation();
        conversation.setUser(new ArrayList<>(Arrays.asList(senderID,receiverID)));
        conversation.setLast_message(chat.getMessage());
        conversation.setLast_message_time(chat.getMessage_time());

        db.collection(ConversationTable.CONVERSATION_TABLENAME).add(conversation).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                db.collection(ConversationTable.CONVERSATION_TABLENAME)
                        .document(task.getResult().getId())
                        .update(ConversationTable.CONVERSATION_CONVERSATIONID,task.getResult().getId());
                conversation.setConversationID(task.getResult().getId());
            }
        });
    }

    @Override
    public void SendMessage(Chat chat,String receiverID ) {
        LoadConversation(chat.getSenderID(),receiverID,((isSuccess, e, conversation,type) -> {
            if(isSuccess){
                chat.setConversationID(conversation.getConversationID());
                db.collection(ChatTable.CHAT_TABLENAME).add(chat).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        db.collection(ChatTable.CHAT_TABLENAME).document(task.getResult().getId())
                                .update(ChatTable.CHAT_MESSAGEID,task.getResult().getId());
                        chat.setMessageID(task.getResult().getId());
                        UpdateConversation(chat);
                    }
                });
            }
            else {
                CreateConversation(chat.getSenderID(),receiverID,chat);
                SendMessage(chat,receiverID);
            }
        }));
    }

    @Override
    public void SendImage(ArrayList<Uri> listImage, String receiverID, onUploadImage listener) {
        LoadConversation(FirebaseAuth.getInstance().getUid(),receiverID,((isSuccess, e, conversation, type) -> {
            if(isSuccess){
                for(int i = 0; i<listImage.size();i++){
                    Chat chat = new Chat();
                    chat.setConversationID(conversation.getConversationID());
                    chat.setImage(true);
                    chat.setSenderID(FirebaseAuth.getInstance().getUid());
                    chat.setMessage_time(Timestamp.now());
                    //Put hình ảnh
                    StorageReference storageReference = storage.getReference().child("Message/"+FirebaseAuth.getInstance().getUid()+"/").child(UUID.randomUUID().toString());
                    storageReference.putFile(listImage.get(i)).addOnCompleteListener(task -> {
                       if(task.isSuccessful()){
                           storageReference.child("Message/"+FirebaseAuth.getInstance().getUid());
                           storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   String imgURI = uri.toString();
                                   chat.setMessage(imgURI);
                                   db.collection(ChatTable.CHAT_TABLENAME).add(chat).addOnCompleteListener(task1 -> {
                                      if(task1.isSuccessful()){
                                          chat.setMessageID(task1.getResult().getId());
                                          db.collection(ChatTable.CHAT_TABLENAME).document(chat.getMessageID())
                                                  .update(ChatTable.CHAT_MESSAGEID,chat.getMessageID());
                                          UpdateConversation(chat);
                                          listener.onFinish(isSuccess,e);
                                      }
                                   });
                               }
                           });
                       }
                    });

                }
            }
        }));
    }

    @Override
    public void UpdateConversation(Chat chat) {
        if(chat.isImage()){
            db.collection(ConversationTable.CONVERSATION_TABLENAME).document(chat.getConversationID())
                    .update(ConversationTable.CONVERSATION_LASTMESSAGE,ChatTable.CHAT_MESSAGEIMAGE,
                            ConversationTable.CONVERSATION_LASTMESSAGETIME,chat.getMessage_time());
        }
        else {
            db.collection(ConversationTable.CONVERSATION_TABLENAME).document(chat.getConversationID())
                    .update(ConversationTable.CONVERSATION_LASTMESSAGE,chat.getMessage(),
                            ConversationTable.CONVERSATION_LASTMESSAGETIME,chat.getMessage_time());
        }

    }



}
