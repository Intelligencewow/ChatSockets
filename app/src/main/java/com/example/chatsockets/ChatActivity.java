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

    MessagesAdapter messagesAdapter;
    RecyclerView MessagesRecyclerView;
    List<Message> MessageList;

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

        MessagesRecyclerView = findViewById(R.id.MessageSentRecyclerView);
        MessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MessageList = new ArrayList<>();
        MessageList.add(new Message("Olá", true));
        MessageList.add(new Message("Bom dia",false));
        MessageList.add(new Message("Boa noite",true));

        messagesAdapter = new MessagesAdapter(this,MessageList);
        MessagesRecyclerView.setAdapter(messagesAdapter);
        Log.i("SentMessage", "terminei a recyclerVIew ");



    }
}