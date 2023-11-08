package com.example.chatties.Model;

import com.google.firebase.firestore.FirebaseFirestore;

public class RequestModel implements IRequestModel {
    FirebaseFirestore db;

    public RequestModel() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void CreateRequest(String senderID, String receiverID) {
    }
}
