package com.example.chatsockets;

import java.util.UUID;

public class Message {

    private String id;
    private String text;

    public Message(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
