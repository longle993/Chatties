package com.example.chatties.Entity;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Conversation implements Comparable<Conversation> {
    String conversationID;
    String last_message;
    Timestamp last_message_time;
    ArrayList<String> user;
    public Conversation(){}

    public Conversation(String conversationID, String last_message, Timestamp last_message_time, ArrayList<String> user) {
        this.conversationID = conversationID;
        this.last_message = last_message;
        this.last_message_time = last_message_time;
        this.user = user;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public Timestamp getLast_message_time() {
        return last_message_time;
    }

    public void setLast_message_time(Timestamp last_message_time) {
        this.last_message_time = last_message_time;
    }


    public ArrayList<String> getUser() {
        return user;
    }

    public void setUser(ArrayList<String> user) {
        this.user = user;
    }

    @Override
    public int compareTo(Conversation o) {
        return this.last_message_time.compareTo(o.last_message_time);
    }
}
