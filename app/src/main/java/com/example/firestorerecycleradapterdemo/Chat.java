package com.example.firestorerecycleradapterdemo;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

/*Chat Model*/

public class Chat {

    String userid,sender, message;
    Date timestamp;

    public Chat(String userid, String sender, String message, Date timestamp) {
        this.userid = userid;
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Chat(){

    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
