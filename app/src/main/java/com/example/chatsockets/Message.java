package com.example.chatsockets;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message {

    private String text;
    private String sender;
    private boolean isSent;
    private String localTime;

    public Message(String text,boolean isSent){
        this.text = getTextFromMessage(text);
        this.sender = getSenderFromMessage(text);
        this.isSent = isSent;
        this.localTime = getTimeFormatted();
    }

    private String getTimeFormatted(){
        LocalTime now = LocalTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        String TimeFormatted = now.format(format);

        return TimeFormatted;

    }

    private String getTextFromMessage(String message){
        String[] parts = message.split(":", 2);
        return parts[1];
    }

    private String getSenderFromMessage(String message){
        String[] parts = message.split(":", 2);
        return parts[0];
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public boolean isSent() {
        return isSent;
    }

    public String getLocalTime() {
        return localTime;
    }
}
