package com.example.chatties.Model;

import com.example.chatties.Entity.Chat;
import com.example.chatties.Entity.ChatTable;
import com.example.chatties.Entity.Conversation;
import com.example.chatties.Entity.ConversationTable;
import com.example.chatties.Entity.User;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageModel implements IMessageModel{
    FirebaseFirestore db;
    UserModel userModel;

    public MessageModel(){
        db = FirebaseFirestore.getInstance();
        userModel = new UserModel();
    }
    @Override
    public void LoadConversation(String senderID, String receiverID, onLoadConverListener listener) {
        db.collection(ConversationTable.CONVERSATION_TABLENAME)
                .whereArrayContains(ConversationTable.CONVERSATION_USER, FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       for(QueryDocumentSnapshot document : task.getResult()) {
                           ArrayList<String> listUser = (ArrayList<String>) document.get(ConversationTable.CONVERSATION_USER);
                           for (int i = 0; i < listUser.size(); i++) {
                               if (receiverID.equals(listUser.get(i))) {
                                   Conversation conversation = new Conversation();
                                   conversation.setConversationID(document.getId());
                                   conversation.setLast_message(document.getString(ConversationTable.CONVERSATION_LASTMESSAGE));
                                   conversation.setLast_message_time(document.getTimestamp(ConversationTable.CONVERSATION_LASTMESSAGETIME));
                                   conversation.setUser(listUser);
                                   listener.onFinish(true,null,conversation);
                               }
                           }
                       }}});
    }


    @Override
    public void LoadConverForUser(onLoadConverListener listener) {
        db.collection(ConversationTable.CONVERSATION_TABLENAME)
                .whereArrayContains(ConversationTable.CONVERSATION_USER,FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot dc: task.getResult()){
                            Conversation conversation = new Conversation();
                            conversation.setConversationID(dc.getId());
                            conversation.setLast_message(dc.getString(ConversationTable.CONVERSATION_LASTMESSAGE));
                            conversation.setLast_message_time(dc.getTimestamp(ConversationTable.CONVERSATION_LASTMESSAGETIME));
                            conversation.setUser((ArrayList<String>) dc.get(ConversationTable.CONVERSATION_USER));

                            ArrayList<User> listUser = new ArrayList<>();
                            for(int i = 0; i< conversation.getUser().size();i++){
                                userModel.GetUser(conversation.getUser().get(i),((isSuccess, e, user) -> {
                                    listUser.add(user);
                                }));
                            }
                            listener.onFinish(true,null,conversation);
                        }
                    }
                    else {
                        task.getException().printStackTrace();
                    }
                });
    }

    @Override
    public void LoadChat(String senderID, String receiverID, onLoadChatListener listener) {
        LoadConversation(senderID, receiverID, (isSuccess, e, conversation) -> {
            if (isSuccess) {
                db.collection(ChatTable.CHAT_TABLENAME)
                        .whereEqualTo(ChatTable.CHAT_CONVERSATIONID, conversation.getConversationID())
                        .addSnapshotListener((querySnapshot, error) -> {
                            if (error != null) {
                                // Xử lý lỗi ở đây
                                listener.onFinish(false, error, null);
                                return;
                            }
                            if (querySnapshot != null) {
                                for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                                    String messageID = dc.getDocument().getId();
                                    String conversationID = dc.getDocument().getString(ChatTable.CHAT_CONVERSATIONID);
                                    String message = dc.getDocument().getString(ChatTable.CHAT_MESSAGE);
                                    Timestamp messageTime = dc.getDocument().getTimestamp(ChatTable.CHAT_MESSTIME);
                                    String id = dc.getDocument().getString(ChatTable.CHAT_SENDERID);

                                    Chat chat = new Chat(conversationID, messageID, message, messageTime, id);
                                    if (dc.getType() == DocumentChange.Type.ADDED ) {
                                        listener.onFinish(true, null, chat);
                                    }
                                }
                            }
                        });
            }
        });

    }

    @Override
    public void CreateConversation(String senderID, String receiverID,Chat chat,onCreateConverListener listener) {
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
                listener.onCreate(true,null,conversation);
            }
            else {
                listener.onCreate(false,task.getException(),null);
            }
        });
    }

    @Override
    public void SendMessage(Chat chat,String receiverID,onSendingListener listener) {
        LoadConversation(chat.getSenderID(),receiverID,((isSuccess, e, conversation) -> {
            if(isSuccess){
                chat.setConversationID(conversation.getConversationID());
                db.collection(ChatTable.CHAT_TABLENAME).add(chat).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        db.collection(ChatTable.CHAT_TABLENAME).document(task.getResult().getId())
                                .update(ChatTable.CHAT_MESSAGEID,task.getResult().getId());
                        chat.setMessageID(task.getResult().getId());
                        listener.onFinish(true,null,chat);
                    }
                    else{
                        listener.onFinish(false,task
                                .getException(),chat);
                    }

                });
            }
            else {
                CreateConversation(chat.getSenderID(),receiverID,chat,((isSuccess1, e1, newConver) -> {
                    if(isSuccess1){
                        SendMessage(chat,receiverID,((isSuccess2, e2,chat1) -> {
                            if(isSuccess){
                                listener.onFinish(isSuccess2,e2,chat1);
                            }
                            else {
                                listener.onFinish(isSuccess2,e2,chat1);
                            }
                        }));
                    }
                }));
            }
        }));
    }

}
