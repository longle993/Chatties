package com.example.chatties.Entity;

import com.google.firebase.Timestamp;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;

public class Chat implements Comparable<Chat>{
    String conversationID;
    String messageID;
    String message;
    Timestamp message_time;
    String senderID;
    boolean isImage;

    public Chat(String conversationID, String messageID, String message, Timestamp message_time, String senderID, boolean isImage) {
        this.conversationID = conversationID;
        this.messageID = messageID;
        this.message = message;
        this.message_time = message_time;
        this.senderID = senderID;
        this.isImage = isImage;
    }

    public Chat(){}

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getMessage_time() {
        return message_time;
    }

    public void setMessage_time(Timestamp message_time) {
        this.message_time = message_time;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    @Override
    public int compareTo(Chat o) {
        return this.message_time.compareTo(o.message_time);
    }
    private boolean isHideTimeMessage;

    public boolean isHideTimeMessage() {
        return isHideTimeMessage;
    }

    public void setHideTimeMessage(boolean hideTimeMessage) {
        isHideTimeMessage = hideTimeMessage;
    }
}
