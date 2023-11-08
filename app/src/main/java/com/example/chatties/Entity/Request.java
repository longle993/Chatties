package com.example.chatties.Entity;

public class Request {
    String requestID;
    String senderID;
    String receiverID;
    boolean isConfirm;

    public Request() {
    }

    public Request(String requestID, String senderID, String receiverID, boolean isConfirm) {
        this.requestID = requestID;
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }
}
