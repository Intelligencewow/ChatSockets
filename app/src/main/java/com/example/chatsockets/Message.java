package com.example.chatsockets;

import java.util.UUID;

public class Message {

    private String id;
    private String text;
    private boolean isSent;

    public Message(String text, boolean isSent){
        this.text = text;
        this.isSent = isSent;
    }

    public String getText() {
        return text;
    }

    public boolean isSent() {
        return isSent;
    }
}
