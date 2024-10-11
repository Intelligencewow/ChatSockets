package com.example.chatsockets;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements MessageListener{

    MessagesAdapter messagesAdapter;
    RecyclerView messagesRecyclerView;
    List<Message> messageList;
    String message;

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

        Server server = new Server();
        server.setMessageListener(this);

        Client client = new Client();
        client.setMessageListener(this);

        Log.i("SentMessage", "onCreate: ChatActivity ");

        messagesRecyclerView = findViewById(R.id.MessageSentRecyclerView);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageList = new ArrayList<>();
        messageList.add(new Message("Olá", true));
        messageList.add(new Message("Bom dia",false));
        messageList.add(new Message("Boa noite",true));

        messagesAdapter = new MessagesAdapter(this, messageList);
        messagesRecyclerView.setAdapter(messagesAdapter);
        Log.i("SentMessage", "terminei a recyclerVIew ");

       EditText etMessage = findViewById(R.id.TIET_message);

       client.recieveMessage();



        etMessage.setOnEditorActionListener((v, actionId, event) -> {
            message = etMessage.getText().toString();
            etMessage.getText().clear();
            client.exchangeMessage(message);
            hideKeyboad(v);
            addMessage(new Message(message,true));
            return true;
        });
    }
    public void hideKeyboad(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void addMessage(Message message){
        messageList.add(message);
        messagesAdapter.notifyItemInserted(messageList.size() - 1);

    }

    @Override
    public void onMessageReceived(Message message) {
        addMessage(message);
    }
}