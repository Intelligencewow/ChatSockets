package com.example.chatsockets;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    SentMessagesAdapter sentMessagesAdapter;
    ReceivedMessagesAdapter receivedMessagesAdapter;
    RecyclerView sentMessagesRecyclerView;
    RecyclerView receivedMessagesRecyclerView;
    List<Message> myMessageList;
    List<Message> theyMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.i("SentMessage", "onCreate: ChatActivity ");

        sentMessagesRecyclerView = findViewById(R.id.MessageSentRecyclerView);
        sentMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myMessageList = new ArrayList<>();
        myMessageList.add(new Message("Olá"));
        myMessageList.add(new Message("Bom dia"));
        myMessageList.add(new Message("Boa noite"));

        sentMessagesAdapter = new SentMessagesAdapter(this,myMessageList);
        sentMessagesRecyclerView.setAdapter(sentMessagesAdapter);
        Log.i("SentMessage", "terminei a recyclerVIew ");

        receivedMessagesRecyclerView = findViewById(R.id.MessageReceivedRecyclerView);
        receivedMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        theyMessageList = new ArrayList<>();
        theyMessageList.add(new Message("Hello"));
        theyMessageList.add(new Message("Good Day"));
        theyMessageList.add(new Message("Good Night"));
        
        receivedMessagesAdapter = new ReceivedMessagesAdapter(this, theyMessageList);
        receivedMessagesRecyclerView.setAdapter(receivedMessagesAdapter);
        


    }
}