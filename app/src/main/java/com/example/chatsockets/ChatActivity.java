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

        messagesRecyclerView = findViewById(R.id.MessageSentRecyclerView);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageList = new ArrayList<>();

        messagesAdapter = new MessagesAdapter(this, messageList);
        messagesRecyclerView.setAdapter(messagesAdapter);

       EditText etMessage = findViewById(R.id.TIET_message);

       //server.recieveMessage();
       //client.recieveMessage();


        etMessage.setOnEditorActionListener((v, actionId, event) -> {
            message = etMessage.getText().toString();
            etMessage.getText().clear();
            try {
                client.exchangeMessage(message);
                addMessage(new Message(message,true));

            } catch (Exception e) {
                server.exchangeMessage(message);
                addMessage(new Message(message,false));
            }
            hideKeyboad(v);
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
        Log.i("SERVER", "addMessage: ERa pra funcionar");
        runOnUiThread(() -> {
            messageList.add(message);
            messagesAdapter.notifyItemInserted(messageList.size() - 1);

        });
    }

    @Override
    public void onMessageReceived(Message message) {
        addMessage(message);
    }
}